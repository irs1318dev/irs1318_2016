package frc.robot.driver.controltasks;

import frc.robot.driver.Operation;

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
        this.setDigitalOperationState(Operation.IntakeExtend, this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.IntakeExtend, this.extend);
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.IntakeExtend, this.extend);
    }

    @Override
    public boolean hasCompleted()
    {
        return true;
    }
}
