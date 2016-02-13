package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterSpinUpTask extends TimedTask implements IControlTask
{
    boolean distance;
    
    // True is a far shot, false is a close shot.
    public ShooterSpinUpTask(boolean distance)
    {
        super(TuningConstants.SHOOTER_SPIN_UP_DURATION);
        this.distance = distance;
    }
    
    @Override
    public void begin()
    {
        super.begin();
        
        setDigitalOperationState(Operation.ShooterSpin, true);
        
        if (distance)
        {
            setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY);
        }
        else 
        {
            setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY);
        }
    }
    
    @Override
    public void stop()
    {
        super.stop();
        setDigitalOperationState(Operation.ShooterSpin, false);
        setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

}
