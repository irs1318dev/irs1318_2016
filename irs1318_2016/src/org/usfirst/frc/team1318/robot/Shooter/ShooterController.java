package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Common.Helpers;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.General.PowerManager;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;

/**
 * Controller for the shooter. Needs to contain velocity PID.
 * @author Corbin
 *
 */
public class ShooterController implements IController
{
    private final ShooterComponent shooter;
    private final PowerManager powerManager;

    private Driver driver;
    private PIDHandler PID;
    
    //private boolean activateTargetingLight = false;

    public ShooterController(ShooterComponent shooter, PowerManager powerManager) 
    {
        this.shooter = shooter;
        this.powerManager = powerManager;

        this.createPIDHandler();
    }

    @Override
    public void update()
    {
        boolean spin = this.driver.getDigital(Operation.ShooterSpin);

        // The actual velocity of the shooter wheel
        double currentRate = this.shooter.getCounterRate();
        int currentTicks = this.shooter.getCounterTicks();
        DashboardLogger.putDouble("shooterRate", currentRate);
        DashboardLogger.putDouble("shooterTicks", currentTicks);

        // The velocity set in the analog operation
        double velocityGoal = this.driver.getAnalog(Operation.ShooterSpeed);
        DashboardLogger.putDouble("shooterVelocityGoal", velocityGoal);

        double power = 0.0;
        boolean shouldLight = false;
        if (spin)
        {
            double speedPercentage = this.shooter.getCounterRate() / TuningConstants.SHOOTER_MAX_COUNTER_RATE;
            shouldLight = velocityGoal != 0.0 && speedPercentage > velocityGoal - TuningConstants.SHOOTER_DEVIANCE && speedPercentage < velocityGoal + TuningConstants.SHOOTER_DEVIANCE;

            // Calculate the power required to reach the velocity goal     
            power = this.PID.calculateVelocity(velocityGoal, currentTicks);

            if (TuningConstants.SHOOTER_SCALE_BASED_ON_VOLTAGE)
            {
                power *= (TuningConstants.SHOOTER_VELOCITY_TUNING_VOLTAGE / this.powerManager.getVoltage());
                power = Helpers.EnforceRange(power, -TuningConstants.SHOOTER_MAX_POWER_LEVEL, TuningConstants.SHOOTER_MAX_POWER_LEVEL);
            }
        }

        this.shooter.setReadyLight(shouldLight);

        // Set the motor power with the calculated value
        this.shooter.setMotorSpeed(power);
        DashboardLogger.putDouble("shooterPower", power);

        // lower the kicker whenever we are rotating in or out, or when we are performing a shot macro
        boolean lowerKicker = this.driver.getDigital(Operation.ShooterLowerKicker)
            || this.driver.getDigital(Operation.IntakeRotatingIn)
            || this.driver.getDigital(Operation.IntakeRotatingOut);

        // control the kicker:
        this.shooter.kick(!lowerKicker);

        boolean extendHood = this.driver.getDigital(Operation.ShooterExtendHood);
        this.shooter.extendHood(extendHood);
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
            TuningConstants.SHOOTER_VELOCITY_PID_KS_DEFAULT,
            -TuningConstants.SHOOTER_MAX_POWER_LEVEL, 
            TuningConstants.SHOOTER_MAX_POWER_LEVEL);
    }
}
