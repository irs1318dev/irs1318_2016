package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class LoadShooterTask extends TimedTask implements IControlTask
{
    public LoadShooterTask()
    {
        super(TuningConstants.SHOOTER_FIRE_TIME);
    }

    @Override
    public void update()
    {
    }
    
    @Override
    public void begin()
    {
        super.begin();
        setDigitalOperationState(Operation.ShooterLoad, true);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        setDigitalOperationState(Operation.ShooterEnable, false);
        setDigitalOperationState(Operation.ShooterLoad, false);
    }

    @Override
    public void end()
    {
        setDigitalOperationState(Operation.ShooterLoad, false);
        setDigitalOperationState(Operation.ShooterEnable, false);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

}
