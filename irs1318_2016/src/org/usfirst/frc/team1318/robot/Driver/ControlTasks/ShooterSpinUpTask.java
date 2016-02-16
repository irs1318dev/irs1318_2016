package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterSpinUpTask extends TimedTask implements IControlTask
{
    private boolean extendHood;
    private double shooterVelocity;
    
    // True is a far shot, false is a close shot.
    public ShooterSpinUpTask(boolean extendHood, double shooterVelocity)
    {
        super(TuningConstants.SHOOTER_SPIN_UP_DURATION);
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
    }

    @Override
    public void update()
    {
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }
}
