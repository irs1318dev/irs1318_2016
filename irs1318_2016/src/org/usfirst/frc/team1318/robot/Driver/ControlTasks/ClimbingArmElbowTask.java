package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * First (or last) step of the climbing macro. Can be used to put the elbow up or down.
 * @author Corbin
 *
 */
public class ClimbingArmElbowTask extends TimedTask
{
    private final boolean up;

    /**
     * Initialize a task that moves the climbing arm at the elbow
     * @param up - whether to move the climbing arm vertically up (or down)
     */
    public ClimbingArmElbowTask(boolean up)
    {
        super(TuningConstants.CLIMBING_ARM_ELBOW_UP_DURATION);
        this.up = up;
    }

    @Override
    public void begin()
    {
        this.setDigitalOperationState(Operation.ClimbingArmElbowUp, this.up);
        this.setDigitalOperationState(Operation.ClimbingArmElbowDown, !this.up);
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
