package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterKickTask extends TimedTask implements IControlTask
{
    public ShooterKickTask()
    {
        super(TuningConstants.SHOOTER_FIRE_DURATION);
    }

    @Override
    public void update()
    {
    }
    
    @Override
    public void begin()
    {
        super.begin();
        this.setDigitalOperationState(Operation.ShooterLowerKicker, false);
    }
    
    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setDigitalOperationState(Operation.ShooterExtendHood, false);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

}
