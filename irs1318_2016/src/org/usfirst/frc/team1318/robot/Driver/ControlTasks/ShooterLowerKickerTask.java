package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * This is a special version of the kicker that is essentially the macro version of the operation. 
 * It is needed so that I can put it into a macroOperation sequence.
 * @author Corbin_Modica
 *
 */
public class ShooterLowerKickerTask extends ControlTaskBase implements IControlTask
{
    private boolean kick;
    private boolean hasFinished;
    
    public ShooterLowerKickerTask(boolean kick)
    {
        this.kick = kick;
        this.hasFinished = false;
    }
    
    @Override
    public void begin()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterLowerKicker, this.kick);
        
        this.hasFinished = true;
    }
    
    
    @Override
    public void update()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterLowerKicker, this.kick);
        
        this.hasFinished = true;
    }
    
    @Override
    public void stop()
    {
        this.setCurrentAndInterruptedDigitalOperationState(Operation.ShooterSpin, false);
        this.setCurrentAndInterruptedAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }
    
    @Override
    public boolean hasCompleted()
    {
        return this.hasFinished;
    }

    @Override
    public void end()
    {
    }
}
