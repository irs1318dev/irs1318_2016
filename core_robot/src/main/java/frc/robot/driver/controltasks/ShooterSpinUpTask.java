package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.ShooterMechanism;
import frc.robot.driver.Operation;

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

        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        if (this.extendHood)
        {
            this.setDigitalOperationState(Operation.IntakeExtend, true);
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        this.setDigitalOperationState(Operation.ShooterExtendHood, this.extendHood);
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
