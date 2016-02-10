package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

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

        //Extend climbing arm, retract climbing arm, or stop climbing arm when appropriate
        if(this.driver.getDigital(Operation.ClimbingArmExtend))
        {
            climbingArm.setClimbingSpeed(TuningConstants.CLIMBING_ARM_MAX_SPEED);
        }
        else if(this.driver.getDigital(Operation.ClimbingArmRetract))
        {
            climbingArm.setClimbingSpeed(-TuningConstants.CLIMBING_ARM_MAX_SPEED);
        }
        else
        {
            climbingArm.setClimbingSpeed(0.0);
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
