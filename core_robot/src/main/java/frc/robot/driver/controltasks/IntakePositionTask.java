package frc.robot.driver.controltasks;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

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
        this.setDigitalOperationState(DigitalOperation.IntakeExtend, this.extend);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(DigitalOperation.IntakeExtend, this.extend);
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.IntakeExtend, this.extend);
    }

    @Override
    public boolean hasCompleted()
    {
        return true;
    }
}
