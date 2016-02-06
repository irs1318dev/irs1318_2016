package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;

/**
 * Controller for the shooter. Needs to contain velocity PID.
 * @author Corbin_Modica
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
        createPIDHandler();
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(Operation.ShooterEnable))
        {
            // The desired percentage of the max velocity
            double velocityGoal = 0.0;
            
            // The actual velocity of the shooter wheel
            double currentTicks = this.shooter.getCounterTicks();
            
            // The velocity set in the analog operaton
            double setVelocity = this.driver.getAnalog(Operation.ShooterSpeed);
            
            // Set the velocity goal as the desired velocity, multipled by the max level and the tuning constant
            velocityGoal = setVelocity * TuningConstants.SHOOTER_K1;
            velocityGoal *= TuningConstants.SHOOTER_MAX_POWER_LEVEL;
            
            double power;
            
            // Calculate the power required to reach the velocity goal     
            power = this.PID.calculateVelocity(velocityGoal, currentTicks);
            
            // Set the motor power with the calculated value
            this.shooter.setMotorSpeed(power);
        }
        else 
        {
            // Zero if its not enabled, cause... yeah
            this.shooter.setMotorSpeed(0.0);
        }
        
        if (this.driver.getDigital(Operation.ShooterLoad))
        {
            this.shooter.kick(true);
        }
        else 
        {
            this.shooter.kick(false);
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
        this.PID = new PIDHandler("shooter", 
            TuningConstants.SHOOTER_VELOCITY_PID_KP_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KI_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KD_DEFAULT, 
            TuningConstants.SHOOTER_VELOCITY_PID_KF_DEFAULT, 
            -TuningConstants.SHOOTER_MAX_POWER_LEVEL, 
            TuningConstants.SHOOTER_MAX_POWER_LEVEL);
    }
}
