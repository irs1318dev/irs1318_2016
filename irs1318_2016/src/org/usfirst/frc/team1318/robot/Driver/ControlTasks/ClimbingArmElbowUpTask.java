package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * First (or last) step of the climbing macro. Can be used to stand or sit.
 * @author Corbin_Modica
 *
 */
public class ClimbingArmElbowUpTask extends TimedTask
{
    private boolean up;

    // True is, well, up...
    public ClimbingArmElbowUpTask(boolean up)
    {
        super(TuningConstants.CLIMBING_ARM_ELBOW_UP_DURATION);
        this.up = up;
    }

    @Override
    public void begin()
    {
        if (up)
        {
            this.setDigitalOperationState(Operation.ClimbingArmElbowUp, true);
        }
        else
        {
            this.setDigitalOperationState(Operation.ClimbingArmElbowUp, false);
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
