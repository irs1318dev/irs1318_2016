package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.Operation;

public class IntakePositionTask extends ControlTaskBase
{
    private final boolean extend;

    public IntakePositionTask(boolean extend)
    {
        this.extend = extend;
    }

    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.IntakeBaseExtend, this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeBaseExtend, this.extend);
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.IntakeBaseExtend, this.extend);
    }

    @Override
    public boolean hasCompleted()
    {
        return true;
    }
}
