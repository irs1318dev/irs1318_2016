 package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Autonomous.AutonomousDriver;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveDistanceTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveRouteTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveTimedTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.IntakeExtendTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.PIDBrakeTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.SequentialTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterKickerTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinDownTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.StingerTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.TurnTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.WaitTask;
import org.usfirst.frc.team1318.robot.Driver.User.UserDriver;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main class for the FRC 2016 Stronghold Competition
 * Robot for IRS1318 - Gimli
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
    private static final String AUTONOMOUS_ROUTINE_PREFERENCE_KEY = "a.routine value";

    // Driver.  This could either be the UserDriver (joystick) or the AutonomousDriver
    private Driver driver;

    // Components
    private ComponentManager components;

    // Controllers
    private ControllerManager controllers;

    // DipSwitches for selecting autonomous mode
    private DigitalInput dipSwitchA;
    private DigitalInput dipSwitchB;
    private DigitalInput dipSwitchC;

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
        
        this.dipSwitchA = new DigitalInput(ElectronicsConstants.AUTONOMOUS_DIP_SWITCH_A);
        this.dipSwitchB = new DigitalInput(ElectronicsConstants.AUTONOMOUS_DIP_SWITCH_B);
        this.dipSwitchC = new DigitalInput(ElectronicsConstants.AUTONOMOUS_DIP_SWITCH_C);
        DashboardLogger.putString(Robot.ROBOT_STATE_LOG_KEY, "Init");
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
        // reset the position manager so that we consider ourself at the origin (0,0) and facing the 0 direction.
        this.components.getPositionManager().reset();

        // Find desired autonomous routine.
        IControlTask autonomousRoutine = Robot.GetFillerRoutine();

        DashboardLogger.putBoolean("Dipswitch in DIO 10 reads: ", !this.dipSwitchA.get());
        DashboardLogger.putBoolean("Dipswitch in DIO 11 reads: ", !this.dipSwitchB.get());
        DashboardLogger.putBoolean("Dipswitch in DIO 12 reads: ", !this.dipSwitchC.get());
        

        int routineSelection = 0;
        if (!this.dipSwitchA.get())
        {
            routineSelection += 1;
        }

        if (!this.dipSwitchB.get())
        {
            routineSelection += 2;
        }
        
        if (!this.dipSwitchC.get())
        {
            routineSelection += 4;
        }
        
        // Select autonomous routine based on the dipswitch positions
        switch (routineSelection)
        {
            case 0://Neither switches flipped
                autonomousRoutine = Robot.GetFillerRoutine();
                break;

            case 1://Switch A flipped
                autonomousRoutine = Robot.GetDriveTimedRoutine(
                    TuningConstants.AUTONOMOUS_TIME_SLOW,
                    0.0,
                    TuningConstants.DRIVETRAIN_AUTONOMOUS_SLOW_VELOCITY);
                break;

            case 2://Switch B flipped
                autonomousRoutine = Robot.GetDriveTimedRoutine(
                    TuningConstants.AUTONOMOUS_TIME_FAST,
                    0.0,
                    TuningConstants.DRIVETRAIN_AUTONOMOUS_FAST_VELOCITY);
                break;

            case 3://Switches A and B flipped
                autonomousRoutine = Robot.GetPortcullisBreachRouteRoutine();
                break;
                
            case 4://Switches C flipped
                autonomousRoutine = Robot.GetDriveStraightAndTurnAndShootCloseRouteRoutine();
                break;
                
            case 5://Switches A and C flipped
                autonomousRoutine = Robot.GetChevalDeFriseBreachRouteRoutine();
                break;
                
            case 6://Switches B and C flipped
                autonomousRoutine = Robot.GetFillerRoutine();
                break;
                
            case 7://Switches A and B and C flipped
                autonomousRoutine = Robot.GetDriveStraightAndTurnAndReaproachAndShootCloseRouteRoutine();
                break;

            default://CANT READ
                autonomousRoutine = Robot.GetFillerRoutine();

                break;
        }

        DashboardLogger.putInteger(Robot.AUTONOMOUS_ROUTINE_PREFERENCE_KEY, routineSelection);

        // Create autonomous driver based on our desired routine
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
        this.driver.update();

        // run each controller
        this.controllers.update();
    }

    /**
     * Gets an autonomous routine that does nothing
     * 
     * @return very long WaitTask
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0);
    }

    /**
     * Gets an autonomous routine that moves the specified velocity for the specified time
     * 
     * @param time - time to drive forward
     * @param xVelocity - velocity in the x to maintain while driving
     * @param yVelocity - velocity in the y to maintain while driving
     * @return DriveTimedTask of specified time, and x and y velocities
     */
    private static IControlTask GetDriveTimedRoutine(double time, double xVelocity, double yVelocity)
    {
        return SequentialTask.Sequence(
            new WaitTask(8.0),
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(0.5, true),
                new DriveTimedTask(time, xVelocity, yVelocity)));
    }

    /**
     * Gets an auto routine that moves through a defense, spins the wheel, and then shoots. 
     * 
     * @return the relevant task
     */
    private static IControlTask GetDriveStraightAndShootDistanceRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveDistanceTask(578.0)),
            new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
            new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
            new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
            new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION));
    }

    /**
     * Gets an auto routine that moves through a defense, spins the wheel, and then shoots. 
     * 
     * @return the relevant task
     */
    private static IControlTask GetDriveStraightAndShootRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio * 640.0,
                    (timeRatio) -> timeRatio * 640.0,
                    10.0)),
            new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
            new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
            new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
            new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION));
    }

    /**
     * Gets an auto routine that moves through the defense, spins the wheel, and then shoots. 
     * 
     * @return the relevant task
     */
    private static IControlTask GetDriveStraightAndTurnAndShootDistanceRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveDistanceTask(640.0)), //578
            new TurnTask(60.0),
            new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
            new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
            new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
            new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION));
    }

    /**
     * Gets an auto routine that moves through the defense, spins the wheel, and then shoots. 
     * 
     * @return the relevant task
     */
    private static IControlTask GetDriveStraightAndTurnAndShootRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio * 550.0,
                    (timeRatio) -> timeRatio * 550.0,
                    7.0)),
            new TurnTask(65.0),
            new DriveRouteTask(
                (timeRatio) -> timeRatio * 290.0,
                (timeRatio) -> timeRatio * 290.0,
                3.0),
            new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
            new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
            new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
            new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION));
    }

    private static IControlTask GetChevalDeFriseRouteAndShootRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio * TuningConstants.START_TO_CHEVAL_DE_FRISE_DISTANCE,
                    (timeRatio) -> timeRatio * TuningConstants.START_TO_CHEVAL_DE_FRISE_DISTANCE,
                    2.0)),
            new StingerTask(TuningConstants.AUTONOMOUS_CHEVAL_BREACH_TIME, true),
            new DriveRouteTask(
                (timeRatio) -> timeRatio * 715.0,
                (timeRatio) -> timeRatio * 715.0,
                7.0),
            new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
            new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
            new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
            new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION));
    }

    private static IControlTask GetPortcullisRouteAndShootRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new StingerTask(TuningConstants.AUTONOMOUS_CHEVAL_BREACH_TIME, true),
            SequentialTask.Sequence(
                new DriveRouteTask(
                    (timeRatio) -> timeRatio * TuningConstants.START_TO_PORTCULLIS_DISTANCE,
                    (timeRatio) -> timeRatio * TuningConstants.START_TO_PORTCULLIS_DISTANCE,
                    2.0)),
                new StingerTask(TuningConstants.AUTONOMOUS_CHEVAL_BREACH_TIME, false),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio * 715.0,
                    (timeRatio) -> timeRatio * 715.0,
                    7.0),
                new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)));
    }

    private static IControlTask GetChevalDeFriseBreachRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 136.0 : 136.0,
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 136.0 : 136.0,
                    2.0)),
            new StingerTask(1.0, true),
            ConcurrentTask.AllTasks(
                new StingerTask(1.0, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 350.0 : 350.0,
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 350.0 : 350.0,
                    5.0)));
    }

    private static IControlTask GetPortcullisBreachRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(0.5, true),
                new StingerTask(0.5, true)),
            SequentialTask.Sequence(
                ConcurrentTask.AllTasks(
                    new DriveRouteTask(
                        (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 250.0 : 250.0,
                        (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 250.0 : 250.0,
                        2.5),
                    new StingerTask(2.5, true)),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 250.0 : 250.0,
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 250.0 : 250.0,
                    2.5)),
            new StingerTask(1.0, false));
    }

    private static IControlTask GetDriveStraightAndTurnAndShootCloseRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 585.0 : 585.0, //525.0
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 585.0 : 585.0, //525.0
                    7.25)), // 7.0
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new TurnTask(75.0, false), //68.0
                    new WaitTask(0.5)),
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, false)),
            new DriveRouteTask(
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 400.0 : 400.0, //420.0
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 400.0 : 400.0, //420.0
                3.25), // 3.5
            ConcurrentTask.AnyTasks(
                SequentialTask.Sequence(
                    new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                    new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                    new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                    new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
                new PIDBrakeTask()));
    }

    private static IControlTask GetDriveStraightAndTurnAndReaproachAndShootCloseRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 585.0 : 585.0, //525.0
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 585.0 : 585.0, //525.0
                    7.25)), // 7.0 after adding new wait logic
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new TurnTask(75.0, false), //68.0
                    new WaitTask(0.5)),
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, false)),
            new DriveRouteTask(
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 400.0 : 400.0, //420.0
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 400.0 : 400.0, //420.0
                3.25), // 3.5, 3.0 after adding new wait logic
            new WaitTask(0.25),
            new DriveRouteTask(
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 100.0 : 100.0,
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 100.0 : 100.0,
                0.25),
            ConcurrentTask.AnyTasks(
                SequentialTask.Sequence(
                    new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                    new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                    new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                    new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
                new PIDBrakeTask()));
    }

    private static IControlTask GetDriveStraightAndCurveAndShootCloseRouteRoutine()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new DriveRouteTask(
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 300.0 : 300.0,
                    (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 300.0 : 300.0,
                    4.0)),
            new DriveRouteTask(
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 490.0 : 490.0,
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 420.0 : 420.0,
                4.0),
            new DriveRouteTask(
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 200.0 : 200.0,
                (timeRatio) -> timeRatio < 0.9 ? timeRatio / 0.9 * 200.0 : 200.0,
                3.0),
            ConcurrentTask.AnyTasks(
                SequentialTask.Sequence(
                    new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                    new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                    new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                    new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
                new PIDBrakeTask()));
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
