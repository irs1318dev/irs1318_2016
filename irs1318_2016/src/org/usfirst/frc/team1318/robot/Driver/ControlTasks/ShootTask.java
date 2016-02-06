package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShootTask extends TimedTask implements IControlTask
{
    /**
     * @author Corbin, Jake and Will
     * The following takes a type of shot (in the form of a boolean), 
     * and then fires the boulder after waiting a period of time.
     */
    boolean distance;
    
    // False for distance is close, true is far
    protected ShootTask(boolean distance)
    {   
        super(TuningConstants.SHOOTER_SPIN_UP_DURATION + TuningConstants.SHOOTER_FIRE_TIME);
        this.distance = distance;
    }

    @Override
    public void begin()
    {
        super.begin();
        setDigitalOperationState(Operation.ShooterEnable, true);
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
    public void update() 
    {
        if (this.timer.get() >= this.startTime + TuningConstants.SHOOTER_SPIN_UP_DURATION)   
        {
            setDigitalOperationState(Operation.Load, true);
        }
    }
    
    @Override
    public void stop()
    {
        super.stop();
        setDigitalOperationState(Operation.ShooterEnable, false);
        setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }

    @Override
    public void end()
    {
        setDigitalOperationState(Operation.Load, false);
        if (distance)
        {
            setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY);
        }
        else 
        {
            setAnalogOperationState(Operation.ShooterSpeed, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY);
        }
        setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return super.hasCompleted();
    }

}
