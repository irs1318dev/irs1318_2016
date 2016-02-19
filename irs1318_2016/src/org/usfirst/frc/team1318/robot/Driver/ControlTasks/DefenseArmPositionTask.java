package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmComponent;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class DefenseArmPositionTask extends ControlTaskBase implements IControlTask
{
    private final double desiredAngleTicks;
    private DefenseArmComponent defenseArm;

    /**
     * Initializes a new DefenseArmPositionTask
     * @param desiredAngle to set the arm to 
     */
    public DefenseArmPositionTask(double desiredAngle)
    {
        this.desiredAngleTicks = desiredAngle;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.defenseArm = this.getComponents().getDefenseArm();
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, true);
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        // Set the desired arm angle converted to ticks
        this.setAnalogOperationState(Operation.DefenseArmSetAngle, this.desiredAngleTicks);
    }

    /**
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        double delta = Math.abs(this.desiredAngleTicks - (this.defenseArm.getEncoderAngle() - this.defenseArm.getZeroAngleOffset())); 
        return delta < TuningConstants.DEFENSE_ARM_POSITIONAL_ACCEPTABLE_DELTA;
    }
}
