package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * Second step of the climbing macro. Can be used to move arm up or down.
 * @author Corbin_Modica
 *
 */
public class ClimbingArmShoulderTask extends TimedTask
{
    private final boolean up;

    /**
     * Initialize a task that moves the climbing arm at the shoulder
     * @param up - whether to move the climbing arm vertically up (or down)
     */
    public ClimbingArmShoulderTask(boolean up)
    {
        super(TuningConstants.CLIMBING_ARM_SHOULDER_UP_DURATION);
        this.up = up;
    }

    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.ClimbingArmShoulderUp, this.up);
        this.setDigitalOperationState(Operation.ClimbingArmShoulderDown, !this.up);
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
