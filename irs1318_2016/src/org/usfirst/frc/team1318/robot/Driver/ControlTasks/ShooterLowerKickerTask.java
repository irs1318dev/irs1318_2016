package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterLowerKickerTask extends TimedTask implements IControlTask
{
    /**
     * Lower the Shooter's kicker
     */
    public ShooterLowerKickerTask(double duration)
    {
        super(duration);
    }

    @Override
    public void update()
    {
    }

    @Override
    public void begin()
    {
        super.begin();
        this.setDigitalOperationState(Operation.ShooterLowerKicker, true);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterLowerKicker, false);
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
