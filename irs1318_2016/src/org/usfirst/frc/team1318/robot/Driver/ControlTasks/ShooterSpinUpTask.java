package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterSpinUpTask extends TimedTask implements IControlTask
{
    private boolean extendHood;
    private double shooterVelocity;

    private static final double deviation = TuningConstants.SHOOTER_DEVIANCE;
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

        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        this.setDigitalOperationState(Operation.ShooterExtendHood, this.extendHood);
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
        super.getComponents().getShooter().setLight(false);
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
        
        double speed = super.getComponents().getShooter().getCounterRate() / TuningConstants.MAX_COUNTER_RATE;
        return  speed > shooterVelocity - deviation &&  speed < shooterVelocity + deviation;
    }
    @Override
    public void end(){
        super.getComponents().getShooter().setLight(true);
    
    }
}
