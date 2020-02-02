package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ShooterKickerTask extends TimedTask implements IControlTask
{
    private final boolean lower;

    public ShooterKickerTask(double duration, boolean lower)
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
        this.setDigitalOperationState(DigitalOperation.ShooterLowerKicker, this.lower);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(DigitalOperation.ShooterSpin, this.lower);
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
