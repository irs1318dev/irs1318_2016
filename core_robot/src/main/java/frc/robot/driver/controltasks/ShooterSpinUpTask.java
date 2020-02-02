package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.ShooterMechanism;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ShooterSpinUpTask extends TimedTask implements IControlTask
{
    private boolean extendHood;
    private double shooterVelocity;

    private ShooterMechanism shooter;

    // True is a far shot, false is a close shot.
    public ShooterSpinUpTask(boolean extendHood, double shooterVelocity, double spinDuration)
    {
        super(spinDuration);
        this.extendHood = extendHood;
        this.shooterVelocity = shooterVelocity;
    }

    @Override
    public void begin()
    {
        super.begin();

        this.shooter = this.getInjector().getInstance(ShooterMechanism.class);

        this.setDigitalOperationState(DigitalOperation.ShooterSpin, true);
        this.setAnalogOperationState(AnalogOperation.ShooterSpeed, this.shooterVelocity);
        if (this.extendHood)
        {
            this.setDigitalOperationState(DigitalOperation.IntakeExtend, true);
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(DigitalOperation.ShooterSpin, false);
        this.setAnalogOperationState(AnalogOperation.ShooterSpeed, 0.0);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(DigitalOperation.ShooterSpin, true);
        this.setAnalogOperationState(AnalogOperation.ShooterSpeed, this.shooterVelocity);
        this.setDigitalOperationState(DigitalOperation.ShooterExtendHood, this.extendHood);
    }

    
    @Override
    public boolean hasCompleted()
    {
        double speed = this.shooter.getCounterRate() / TuningConstants.SHOOTER_MAX_COUNTER_RATE;
        return 
            (speed > this.shooterVelocity - TuningConstants.SHOOTER_DEVIANCE
                && speed < this.shooterVelocity + TuningConstants.SHOOTER_DEVIANCE)
            || super.hasCompleted();
    }

    @Override
    public void end()
    {
    }
}
