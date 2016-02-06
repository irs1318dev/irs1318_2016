package org.usfirst.frc.team1318.robot.Intake;


import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.Timer;

/**
 * Controller for intake. It is designed with two states: Rotating in and rotating out
 * @author Nathan
 *
 */

public class IntakeController implements IController

{
    private final IntakeComponent intake;
    private Driver driver;
    
    
    // Constructor should initialize all of the necessary variables for the controller, and set basic values
    public IntakeController(IntakeComponent intake){
        this.intake = intake;
        //Isn't this contructor exciting?????!!!!!
    }
    
    
    /*
     * // Operation check for the portcullis macro        
        if (driver.getDigital(Operation.DefenseArmUsePositionalMode))
        {
            desiredPosition = driver.getAnalog(Operation.DefenseArmSetAngle);
        }

        // Check for the desire to move the arm to the front or back of the robot
        if (this.driver.getDigital(Operation.DefenseArmForward))
        {
            this.movingToFront = true;
        }
     * */
    @Override
    public void update()
    {
        
      //Current function the intake is performing
        boolean rotatingIn = false;
        boolean rotatingOut = false;
        boolean notRotating = false;
        
        //Check for desire to intake ball
        if(driver.getDigital(Operation.IntakeRotatingIn)){
            rotatingIn = true;
            rotatingOut = false;
            notRotating = false;
            
        }
        
        //Check for desire to release ball
        if(driver.getDigital(Operation.IntakeRotatingOut)){
            rotatingIn = false;
            rotatingOut = true;
            notRotating = false;
        }
        
        //Check for desire not to run the intake
        if(driver.getDigital(Operation.IntakeNotRotating)){
            rotatingIn = false;
            rotatingOut = false;
            notRotating = true;
        }
        
        if(rotatingIn){
            
        }
        
        if(rotatingOut){
            
        }
        
        if(notRotating){
            
        }
       
    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDriver(Driver driver)
    {
        // TODO Auto-generated method stub
        
    }

}
