package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

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
        this.setDigitalOperationState(Operation.ShooterLowerKicker, this.lower);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, this.lower);
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
