package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * This is a special version of the kicker that is essentially the macro version of the operation. 
 * It is needed so that I can put it into a macroOperation sequence.
 * @author Corbin_Modica
 *
 */
public class ShooterKickTask extends ControlTaskBase implements IControlTask
{
    private boolean kick;
    private boolean hasFinished;
    
    public ShooterKickTask(boolean kick)
    {
        this.kick = kick;
        this.hasFinished = false;
    }
    
    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.ShooterLowerKicker, kick);
        this.hasFinished = true;
    }
    
    @Override
    public boolean hasCompleted()
    {
        return this.hasFinished;
    }
    
    @Override
    public void update()
    {
    }
    
    @Override
    public void stop()
    {
    }

    @Override
    public void end()
    {

    }
}
