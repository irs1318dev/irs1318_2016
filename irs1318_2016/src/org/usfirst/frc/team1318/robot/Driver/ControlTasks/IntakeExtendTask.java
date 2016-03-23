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
        if (lower)
        {
            this.setDigitalOperationState(Operation.IntakeExtend, this.lower);
        }
        else 
        {
            this.setDigitalOperationState(Operation.IntakeRetract, this.lower);

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
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }
}
