package org.usfirst.frc.team1318.robot.Climber;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ClimberController implements IController
{
    private ClimberComponent climbingArm;
    private Driver driver;

    public ClimberController(ClimberComponent climbingArm)
    {
        this.climbingArm = climbingArm;
    }

    @Override
    public void update()
    {   
        // Extend or retract the arm if desired
        if (this.driver.getDigital(Operation.ClimberArmUp))
        {
            this.climbingArm.extendArm(true);
        }
        else if (this.driver.getDigital(Operation.ClimberArmDown))
        {
            this.climbingArm.extendArm(false);
        }
        
        // Set the speed of the winch according to desire     
        double currentWinchSpeed = 0.0;
        if (this.driver.getDigital(Operation.ClimberWinchExtend))
        {
            currentWinchSpeed = this.driver.getAnalog(Operation.ClimberWinchSpeed);
        }
        
        this.climbingArm.setWinchSpeed(currentWinchSpeed);
        
        // Set the speed of the hook according to desire
        double currentHookSpeed = 0.0;
        if (this.driver.getDigital(Operation.ClimberFiringPinExtend))
        {
            currentHookSpeed = TuningConstants.CLIMBER_HOOK_MAX_SPEED;
        }
        
        this.climbingArm.setFiringPinSpeed(currentHookSpeed);
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