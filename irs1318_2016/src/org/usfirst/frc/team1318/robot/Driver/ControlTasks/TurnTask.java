package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;

/**
 * Task that turns the robot a certain amount clockwise or counterclockwise in-place using Positional PID.
 * 
 */
public class TurnTask extends MoveDistanceTaskBase implements IControlTask
{
    private final double degrees;

    /**
     * Initializes a new TurnTask
     * @param degrees from the current orientation to rotate (positive means turn right/clockwise, negative means turn left/counter-clockwise)
     */
    public TurnTask(double degrees)
    {
        this(degrees, true);
    }

    /**
     * Initializes a new TurnTask
     * @param degrees from the current orientation to rotate (positive means turn right/clockwise, negative means turn left/counter-clockwise)
     */
    public TurnTask(double degrees, boolean resetPositionOnEnd)
    {
        super(resetPositionOnEnd);

        this.degrees = degrees;
    }

    /**
     * Determine the final encoder distance
     */
    @Override
    protected void determineFinalEncoderDistance()
    {
        double arcLength = Math.PI * HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE * (this.degrees / 360.0);
        this.desiredFinalLeftEncoderDistance = this.startLeftEncoderDistance + arcLength;
        this.desiredFinalRightEncoderDistance = this.startRightEncoderDistance - arcLength;
    }
}
