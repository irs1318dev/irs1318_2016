package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterKickTask extends TimedTask implements IControlTask
{
    public ShooterKickTask()
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
        setDigitalOperationState(Operation.ShooterKick, true);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        setDigitalOperationState(Operation.ShooterSpin, false);
        setDigitalOperationState(Operation.ShooterKick, false);
    }

    @Override
    public void end()
    {
        setDigitalOperationState(Operation.ShooterKick, false);
        setDigitalOperationState(Operation.ShooterSpin, false);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

}
