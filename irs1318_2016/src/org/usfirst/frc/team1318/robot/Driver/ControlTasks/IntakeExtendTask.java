package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class IntakeExtendTask extends TimedTask implements IControlTask
{
    private final boolean lower;

    public IntakeExtendTask(double duration, boolean lower)
    {
        super(duration);
        this.lower = lower;
    }

    @Override
    public void update()
    {
    }
    
    @Override
    public void begin()
    {
        super.begin();
        if (this.lower)
        {
            this.setDigitalOperationState(Operation.IntakeExtend, true);
            this.setDigitalOperationState(Operation.IntakeRetract, false);
        }
        else 
        {
            this.setDigitalOperationState(Operation.IntakeExtend, false);
            this.setDigitalOperationState(Operation.IntakeRetract, true);
        }
    }
    
    @Override
    public void stop()
    {
        super.stop();
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.IntakeExtend, false);
        this.setDigitalOperationState(Operation.IntakeRetract, false);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }
}
