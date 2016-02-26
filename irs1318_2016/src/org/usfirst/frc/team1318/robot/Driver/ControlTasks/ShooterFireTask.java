package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ShooterFireTask extends ControlTaskBase implements IControlTask
{
    private boolean hasFinished;
    
    public ShooterFireTask()
    {
        this.hasFinished = false;
    }
    
    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.ShooterLowerKicker, false);
        
        this.hasFinished = true;
    }
    
    @Override
    public void update()
    {  
        this.setDigitalOperationState(Operation.ShooterLowerKicker, false);
        
        this.hasFinished = true;
    }
    
    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }
    
    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.ShooterLowerKicker, true);
        this.setDigitalOperationState(Operation.ShooterExtendHood, false);
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return this.hasFinished;
    }

}
