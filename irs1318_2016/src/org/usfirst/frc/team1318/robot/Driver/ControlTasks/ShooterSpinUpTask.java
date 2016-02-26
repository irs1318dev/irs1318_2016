package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterSpinUpTask extends ControlTaskBase implements IControlTask
{
    private double shooterVelocity;
    private boolean extendHood;
    private boolean hasFinished;

    // True is a far shot, false is a close shot.
    public ShooterSpinUpTask(boolean extendHood, double shooterVelocity)
    {
        this.shooterVelocity = shooterVelocity;
        this.extendHood = extendHood;
        this.hasFinished = false;
    }

    @Override
    public void begin()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterSpin, true);
        this.setCurrentAndInterruptedAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterExtendHood, this.extendHood);
        
        this.hasFinished = true;
    }

    // Doubt it will be called before hasCompleted(), but just in case...
    @Override
    public void update()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterSpin, true);
        this.setCurrentAndInterruptedAnalogOperationState(Operation.ShooterSpeed, this.shooterVelocity);
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterExtendHood, this.extendHood);
        
        this.hasFinished = true;
    }
    
    @Override
    public void stop()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterSpin, false);
        this.setCurrentAndInterruptedAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }

    @Override
    public boolean hasCompleted()
    {
        return this.hasFinished;
    }

    @Override
    public void end()
    {
    }
}
