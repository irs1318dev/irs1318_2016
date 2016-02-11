package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.DigitalInput;

public class ClimbingArmController implements IController
{
    private ClimbingArmComponent climbingArm;
    private Driver driver;
    
    public ClimbingArmController(ClimbingArmComponent climbingArm){
        this.climbingArm = climbingArm;
    }

    @Override
    public void update()
    {
        boolean topLimitSwitch = this.climbingArm.getTopLimitSwitch();
        boolean bottomLimitSwitch = this.climbingArm.getBottomLimitSwitch();
        
        // The operations for raising up and standing will probably only be used in macros, so it should be fine to have it like this
        // The toggle for the side solenoid
        if (this.driver.getDigital(Operation.ClimbingArmUp))
        {
            this.climbingArm.extendOrRetractSideSolenoid(true);
        }
        else
        {
            this.climbingArm.extendOrRetractSideSolenoid(false);
        }
        
        // The toggle for the arm solenoid
        if (this.driver.getDigital(Operation.ClimbingArmStand))
        {
            this.climbingArm.extendOrRetractArmSolenoid(true);
        }
        else
        {
            this.climbingArm.extendOrRetractArmSolenoid(false);
        }
        
        //Extend climbing arm, retract climbing arm, or stop climbing arm when appropriate
        if (this.driver.getDigital(Operation.ClimbingArmExtend))
        {
            this.climbingArm.setClimbingSpeed(TuningConstants.CLIMBING_ARM_MAX_SPEED);
        }
        else if (this.driver.getDigital(Operation.ClimbingArmRetract))
        {
            this.climbingArm.setClimbingSpeed(-TuningConstants.CLIMBING_ARM_MAX_SPEED);
        }
        
        // Check to see if the nut has reached the top of the lead screw, and set the motor speed to 0 if it has.
        if (topLimitSwitch)
        {
            this.climbingArm.setClimbingSpeed(0.0);
        }
        
        // Check to see if the nut has reached the bottom of the lead screw, and set the motor speed to 0 if it has.
        if (bottomLimitSwitch)
        {
            this.climbingArm.setClimbingSpeed(0.0);
        }
    }

    @Override
    public void stop()
    {
        this.climbingArm.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
