package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * Second step of the climbing macro. Can be used to move arm up or down.
 * @author Corbin_Modica
 *
 */
public class ClimbingArmShoulderUpTask extends TimedTask
{
    private boolean up;

    // True is, well, up...
    public ClimbingArmShoulderUpTask(boolean up)
    {
        super(TuningConstants.CLIMBING_ARM_SHOULDER_UP_DURATION);
        this.up = up;
    }

    @Override
    public void begin()
    {
        if (up)
        {
            this.setDigitalOperationState(Operation.ClimbingArmShoulderUp, true);
        }
        else
        {
            this.setDigitalOperationState(Operation.ClimbingArmShoulderUp, false);
        }
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
