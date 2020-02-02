package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

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
            this.setDigitalOperationState(DigitalOperation.IntakeExtend, true);
            this.setDigitalOperationState(DigitalOperation.IntakeRetract, false);
        }
        else 
        {
            this.setDigitalOperationState(DigitalOperation.IntakeExtend, false);
            this.setDigitalOperationState(DigitalOperation.IntakeRetract, true);
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
        this.setDigitalOperationState(DigitalOperation.IntakeExtend, false);
        this.setDigitalOperationState(DigitalOperation.IntakeRetract, false);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }
}
