 package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Compressor.CompressorController;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmController;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainController;
import org.usfirst.frc.team1318.robot.DriveTrain.PositionManager;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Autonomous.AutonomousDriver;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.BreachPortcullisTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveDistanceTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.SequentialTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterKickTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.WaitTask;
import org.usfirst.frc.team1318.robot.Driver.User.UserDriver;
import org.usfirst.frc.team1318.robot.Intake.IntakeController;
import org.usfirst.frc.team1318.robot.Shooter.ShooterController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main class for the FRC 2016 Stronghold Competition
 * Robot for IRS1318 - RobotName
 * 
 * 
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package, you
 * must also update the manifest file in the resource directory.
 * 
 * 
 * General design comments:
 * We have three types of objects:
 * - Driver - describes the driver/operator of the robot ("autonomous" or "user")
 * - Components - describe the electronics of an mechanism and defines the abstract way to control those electronics.
 * - Controllers - define the logic that controls a mechanism given inputs/outputs.
 * 
 * @author Will
 */
public class Robot extends IterativeRobot
{
    // smartdash logging constants
    private static final String ROBOT_STATE_LOG_KEY = "r.s";

    // smartdash other constants 
    private static final String AUTONOMOUS_ROUTINE_PREFERENCE_KEY = "a.routine";

    // Driver.  This could either be the UserDriver (joystick) or the AutonomousDriver
    private Driver driver;

    // Components
    private ComponentManager components;

    // Controllers
    private ControllerManager controllers;

    // DipSwitches for selecting autonomous mode
    private DigitalInput dipSwitchA;
    private DigitalInput dipSwitchB;

    // Position manager - holds position information relative to our starting point
    private PositionManager position;

    /**
     * Robot-wide initialization code should go here.
     * This default Robot-wide initialization code will be called when 
     * the robot is first powered on.  It will be called exactly 1 time.
     */
    public void robotInit()
    {                               
        // create mechanism components
        this.components = new ComponentManager();

        // create controllers for each mechanism
        this.controllers = new ControllerManager(this.components);

        // create position manager
        this.position = new PositionManager(this.components.getDriveTrain());

        DashboardLogger.putString(Robot.ROBOT_STATE_LOG_KEY, "Init");

        this.dipSwitchA = new DigitalInput(ElectronicsConstants.AUTONOMOUS_DIP_SWITCH_A);
        this.dipSwitchB = new DigitalInput(ElectronicsConstants.AUTONOMOUS_DIP_SWITCH_B);
        
    }

    /**
     * Initialization code for disabled mode should go here.
     * This code will be called each time the robot enters disabled mode.
     */
    public void disabledInit()
    {
        if (this.driver != null)
        {
            this.driver.stop();
        }

        if (this.controllers != null)
        {
            this.controllers.stop();
        }

        DashboardLogger.putString(Robot.ROBOT_STATE_LOG_KEY, "Disabled");
    }

    /**
     * Initialization code for autonomous mode should go here.
     * This code will be called each time the robot enters autonomous mode.
     */
    public void autonomousInit()
    {
        // reset the drivetrain component and position manager so that we consider ourself at the origin (0,0) and facing the 0 direction.
        // this.components.getDriveTrain().reset();
        this.position.reset();

        // Find desired autonomous routine.
        IControlTask autonomousRoutine = Robot.GetFillerRoutine();

        int routineSelection = 0;
        if (this.dipSwitchA.get())
        {
            routineSelection += 1;
        }

        if (this.dipSwitchB.get())
        {
            routineSelection += 2;
        }

        //select autonomous routine based on the dipswitch positions
        switch (routineSelection)
        {
            case 0://neither flipped
                //autonomousRoutine = AutonomousPortcullisBreach();
                //break;
            case 1://switch A flipped
            case 2://switch B flipped
            default://both flipped or can't read 
                autonomousRoutine = Robot.GetFillerRoutine();
                break;
        }

        DashboardLogger.putInteger(Robot.AUTONOMOUS_ROUTINE_PREFERENCE_KEY, routineSelection);

        // create autonomous driver based on our desired routine
        this.driver = new AutonomousDriver(autonomousRoutine, this.components);

        this.generalInit();

        // log that we are in autonomous mode
        DashboardLogger.putString(Robot.ROBOT_STATE_LOG_KEY, "Autonomous");
    }

    /**
     * Initialization code for teleop mode should go here.
     * This code will be called each time the robot enters teleop mode.
     */
    public void teleopInit()
    {
        // create driver for user's joystick
        this.driver = new UserDriver(this.components);

        this.generalInit();

        // log that we are in teleop mode
        DashboardLogger.putString(Robot.ROBOT_STATE_LOG_KEY, "Teleop");
    }

    /**
     * General initialization code for teleop/autonomous mode should go here.
     */
    public void generalInit()
    {
        // apply the driver to the controllers
        this.controllers.setDriver(this.driver);
    }

    /**
     * Periodic code for disabled mode should go here.
     * This code will be called periodically at a regular rate while the robot is in disabled mode.
     */
    public void disabledPeriodic()
    {
    }

    /**
     * Periodic code for autonomous mode should go here.
     * This code will be called periodically at a regular rate while the robot is in autonomous mode.
     */
    public void autonomousPeriodic()
    {
        this.generalPeriodic();
    }

    /**
     * Periodic code for teleop mode should go here.
     * This code will be called periodically at a regular rate while the robot is in teleop mode.
     */
    public void teleopPeriodic()
    {
        this.generalPeriodic();
    }

    /**
     * General periodic code for teleop/autonomous mode should go here.
     */
    public void generalPeriodic()
    {
        // update our position
        this.position.update();

        this.driver.update();

        // run each controller
        this.controllers.update();
    }

    /**
     * Gets an autonomous routine that does nothing
     * 
     * @return list of autonomous tasks
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0);
    }
    
    // @author Corbin
    // My first attempt to write an autonomous routine
    // Should move to the portcullis, go through, spin up the shooter, and then shoot.
    @SuppressWarnings("unused")
    private static IControlTask autonomousPortcullisBreach() 
    {
        return new SequentialTask(new IControlTask[]{
            new DriveDistanceTask(1.0),
            new BreachPortcullisTask(),
            new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY),
            new ShooterKickTask()}); 
    }
}

/*







































































































































                                      .                                                             
                                    .;+;+                                                           
                                    .+;;'   `,+'.                                                   
                                    ;';;+:..`` :+'+                                                 
                                    ,'+`    .+;;;;;+                                                
                                     ;,,, .+;;;;;'+++;                                              
                                     ;' `+;;;;;#+'+'+''#:.                                          
                                     '`+';;;'+;+;+++'''+'.                                          
                                     #';;;;#';+'+'''+''+'                                           
                                     ;;;;#;,+;;+;;;'''''':                                          
                                     ';'++'.`+;;'';;''+'',                                          
                                     :#'#+'``.'+++'#++'':`                                          
                                      `';++##```##+.''.##                                           
                                      +++#   #`#  `++++                                             
                                      +'#+ # :#: # ##'+                                             
                                      `#+#   +`+   #'#`                                             
                                       :,.+,+,`:+,+..,                                              
                                       `,:```,`,`.`;,                                               
                                        :+.;``.``;.#;                                               
                                        .'``'+'+'``'.                                               
                                         ,````````..                                                
                                          :```````:                                                 
                                          +``.:,``'                                                 
                                          :```````:                                                 
                                           +`````+                                                  
                                            ';+##                                                   
                                            '```'                                                   
                                           `'```'`                                                  
                                         .+''''''''                                                 
                                        +;;;;;;;;''#                                                
                                       :       `   `:                                               
                                      `,            '                                               
                                      +              '                                              
                                     ,;';,``.``.,,,:;#                                              
                                     +;;;;;;;;;;;;;;;'                                              
                                    ,';;;;;;;;;;;;;;;',                                             
                                    +:;;;;;;';;;;;;;;;+                                             
                                   `.   .:,;+;;:::;.``,                                             
                                   :`       #,       `.`                                            
                                   +       # ;        .;                                            
                                  .;;,`    ,         `,+                                            
                                  +;;;;;;''';;;;;;;';;';                                            
                                  +;;;;;;;';;;;;;;;;;'';;                                           
                                 `';;;;;;';;;;;;;;;;;';;+                                           
                                 + `:;;;;+;;;;;;;;';'''::                                           
                                 '     `:  ```````    ,  ,                                          
                                :       '             ;  +                                          
                                '`     ..             ,  ,                                          
                               ,;;;;;..+,`        ```.':;',                                         
                               +;;;;;;'+;;;;;;;;;;;;;;+;;;+                                         
                               ';;;;;;++;;;;;;;;;;;;;;';;;+                                         
                              `.:';;;;;#;;;;;;;;;;;;;;';;;;`                                        
                              ;    `,; ',:;;';;';;;;;:;``  +                                        
                              +      ; ;              ;    `                                        
                              ;      : +              '    `;                                       
                              ';:`` `` '              :`,:;;+                                       
                             `';;;;'+  +,..```````..:;#;;;;;;.                                      
                             `;;;;;;+  +;;;;;;;;;;;;;':';;;;;#                                      
                             .;;;;;;+  ';;;;;;;;;;;;;;,';;;;` .                                     
                             : `.;;'+  +;;;;;;;;;;;;;','.`    +                                     
                             '      ;  +.,,;:;:;;;,..`: ,     ``                                    
                             +      ,  '              : ;   .;'+                                    
                             +.`   ``  +              ;  ;:;;;;':                                   
                             ';;;';;`  +             .'  ;;;;;;;+                                   
                             ';;;;;'   :+++#++##+#+''',   +;;;;.`.                                  
                             +;;;;;'   +;;::;;;+:+;;'',   ,;;.   +                                  
                            ``:;;;;+   +;;:;;;:+;+;;++;    +     .`                                 
                             `   ``'   +;;;;;;;+;+;;'+;     ,   ;#,                                 
                            .      ;   ';;;;;;;;;;;;++'     + .+``.;                                
                            ``     ;   ';;;;;;+;';;;'+'      #`````:,                               
                             +++;,:.   ':;''++;:';:;'';      +``````,`                              
                             ,```,+    +;;';:;;+;;;;'';      +``````,+                              
                            .``````:   ;:;;++';;;;;;';,      ,``:#``+`.                             
                            ,``````'   `';;;;:;;;;;;+;`     '+``+:'`..'                             
                            ,``````'    +;;;;;;;;;;;''     ;:'``#;;.`++                             
                            ```````;    `;:;;;;;;;;;;#     ':'``++:+`+;                             
                            ```'`.`;     +;;;;;;;;;;;+    :::#``' +#`';                             
                            ,``'`:`#     `';;;;;;;;;;+    +:'.`,. ++`;;                             
                            +`.``+`'     :#;;;;;;;;;;;`   +:# ,`  +;`.'                             
                           ,.`+`.:.      ##;;;;;;;;;;;'   ,'`     ;:+#                              
                           '`;.`+`#      ##+;;;;;;;;;;+          ,::;                               
                           ,+,`:``,     :###;;;;;;;;;:'          +:;`                               
                            '`,,`+      ';##';;;;;;;;;;.         +:#                                
                             '+.+       +;;##;;;;;;;;;;'         ;:;                                
                               `       :;;;+#;;;;;;;;;;+        ;::`                                
                                       +;;;;#+;;;;;;;;;;        +:'                                 
                                       ';;;;+#;;;;;;;;;;.       ;:'                                 
                                      ,;;;;;;#;;;;;;;;;;+      +::.                                 
                                      +;;;;;;'';;;;;;;;;'      +:+                                  
                                     `;;;;;;;;#;;;;;;;;;;`    `;:+                                  
                                     ,;;;;;;;;+;;;;;;;;;;+    ':;,                                  
                                     +;;;;;;;;;+;;;;;;;;;'    +:+                                   
                                    .;;;;;;;;;+,;;;;;;;;;;`   ;;+                                   
                                    ';;;;;;;;;, ';;;;;;:;;,  +;:,                                   
                                    ';;;;;;;;'  +;;;;;;;;;'  +:+                                    
                                   ;;;;;;;;;;+  ,;;;;;;;;;+  ;:'                                    
                                   +;;;;;;;;;    ';;;;;;;;;`;:;`                                    
                                   ;;;;;;;;;+    +;;;;;;;;;+#:+                                     
                                  ';;;;;;;;;:    ;;;;;;;;;;';:'                                     
                                 `';;;;;;;:'      ';;;;;;;;;;:.                                     
                                 .;;;;;;;;;+      +;;;;;;;;;'+                                      
                                 +;;;;;;;;;       ';;;;;;;;;#+                                      
                                `;;;;;;;;;+       `;;;;;;;;;;`                                      
                                +;;;;;;;;;.        +;;;;;;;;;`                                      
                                ';;;;;;;:'         ;;;;;;;;;;;                                      
                               :;;;;;;;;;:         `;;;;;;;;;+                                      
                               +;;;;;;;;;           ';;;;;;;;;`                                     
                               ;;;;;;;;;+           ';;;;;;;;;:                                     
                              ';;;;;;;;;;           ,;;;;;;;;;+                                     
                              ':;;;;;;;'             +;;;;;;;;;                                     
                             .;:;;;;;;;'             +;;;;;;;;;:                                    
                             +;;;;;;;;;`             .;;;;;;;;;+                                    
                            `;;;;;;;;;+               ;:;;;;;;;;`                                   
                            ;;;;;;;;;;.               +;;;;;;;::.                                   
                            ';;;;;;;;'`               :;;;;;;;;:+                                   
                           :;;;;;;;;:'                ';;;;;;;;;'                                   
                           ';;;;;;;;'`                +#;;;;;;;;;`                                  
                          `;;;;;;;;;+                 '';;;;;;;;;+                                  
                          +;;;;;;;;;.                '::;;;;;;;;;+                                  
                          ;;;;;;;;;+                 #:'';;;;;;;;;`                                 
                         .#;;;;;;;;'                `;:+;;;;;;;;;;;                                 
                         ':'';;;;;;                 '::.,;;;;;;;;;+                                 
                        +::::+';;;+                 ':'  +:;;;;;;;;`                                
                       `;;;::::;#+:                `;:+  +;;;;;;;:;;      '#+,                      
                       +#::::::::;'`               +:;,  `;;;;:;;'#';;;;;::;:'`                     
                       ;:''::::::::#`              +:'    ';:;;+'::;;:;::::::''                     
                       +::;+':::::::'.            .:;+    '''+;::;:;:::;:::;':'                     
                        ';;:;'';:::::':           +::.     +:::::::::::::;#;:#                      
                         .''##;#;:;;:::'+        `+;'      ;:;::::::::;'+;:'+                       
                           ` `+:;+:;::;::+       +:;#      ';:::;:+#+';:::+.                        
                              ,+::+#';::;+       ';::      #:;;'+';'''++:`                          
                                '':::;'''#      ,:;;`      #';:;;:+                                 
                                 `:'++;;':       :++       .;;:;;#,                                 
                                       `                    '':``                                   


*/
