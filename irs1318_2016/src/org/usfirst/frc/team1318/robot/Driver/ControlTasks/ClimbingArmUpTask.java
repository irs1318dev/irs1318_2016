package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.Operation;
/**
 * Second step of the climbing macro. Can be used to move arm up or down.
 * @author Corbin_Modica
 *
 */

public class ClimbingArmUpTask extends TimedTask
{
    boolean up;

    // True is, well, up...
    public ClimbingArmUpTask(boolean up)
    {
        super(1.0);
        this.up = up;
    }

    @Override
    public void begin()
    {
        if (up)
        {
            this.setDigitalOperationState(Operation.ClimbingArmUp, true);
        }
        else
        {
            this.setDigitalOperationState(Operation.ClimbingArmUp, false);
        }
    }
    
    @Override
    public void update()
    {
     
    }

    @Override
    public void stop()
    {
        this.getComponents().getClimbingArmComponent().stop();
    }

    @Override
    public void end()
    {
        
    }
}
