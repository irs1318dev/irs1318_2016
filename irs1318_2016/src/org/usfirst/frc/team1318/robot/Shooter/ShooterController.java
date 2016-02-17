package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;

/**
 * Controller for the shooter. Needs to contain velocity PID.
 * @author Corbin
 *
 */
public class ShooterController implements IController
{
    private ShooterComponent shooter;
    private Driver driver;
    private PIDHandler PID;
    
    public ShooterController(ShooterComponent shooter) 
    {
        this.shooter = shooter;
        this.createPIDHandler();
    }

    @Override
    public void update()
    {
        boolean spin = this.driver.getDigital(Operation.ShooterSpin);

        // The actual velocity of the shooter wheel
        double currentTicks = this.shooter.getCounterRate();
        
        // The velocity set in the analog operation
        double velocityGoal = this.driver.getAnalog(Operation.ShooterSpeed);
            
        if (spin)
        {
            // Calculate the power required to reach the velocity goal     
            double power = this.PID.calculateVelocity(velocityGoal, currentTicks);
            
            // Set the motor power with the calculated value
            this.shooter.setMotorSpeed(power);
        }
        else 
        {
            // Zero if we're not spinning...
            this.shooter.setMotorSpeed(0.0);
        }

        boolean kick = this.driver.getDigital(Operation.ShooterKick)
            || !this.driver.getDigital(Operation.IntakeRotatingIn)
            || !this.driver.getDigital(Operation.IntakeRotatingOut);

        if (kick)
        {
            this.shooter.kick(true);
        }
        else 
        {
            this.shooter.kick(false);
        }
        
        boolean hood = this.driver.getDigital(Operation.ShooterExtendHood);
        if (hood)
        {
            this.shooter.hood(true);
        }
        else
        {
            this.shooter.hood(false);
        }
    }

    @Override
    public void stop()
    {
        this.shooter.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    
    public void createPIDHandler() 
    {
        this.PID = new PIDHandler(
            TuningConstants.SHOOTER_VELOCITY_PID_KP_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KI_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KD_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KF_DEFAULT, 
            -TuningConstants.SHOOTER_MAX_POWER_LEVEL, 
            TuningConstants.SHOOTER_MAX_POWER_LEVEL);
    }
}
