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
        
        this.setDigitalOperationState(Operation.ShooterSpin, true);
        
        if (distance)
        {
            this.setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY);
            this.setDigitalOperationState(Operation.ShooterExtendHood, true);
        }
        else 
        {
            this.setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY);
            this.setDigitalOperationState(Operation.ShooterExtendHood, false);
        }
    }
    
    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
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
