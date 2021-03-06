package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;

/**
 * Task that drives the robot a certain distance directly forward or backward using Positional PID.
 * 
 */
public class DriveDistanceTask extends MoveDistanceTaskBase implements IControlTask
{
    private final double distance;

    /**
     * Initializes a new DriveDistanceTask
     * @param distance from the current location to move (positive means move forward, negative means move backwards) in centimeters
     */
    public DriveDistanceTask(double distance)
    {
        this(distance, true);
    }

    /**
     * Initializes a new DriveDistanceTask
     * @param distance from the current location to move (positive means move forward, negative means move backwards) in centimeters
     * @param resetPositionalOnEnd
     */
    public DriveDistanceTask(double distance, boolean resetPositionalOnEnd)
    {
        super(resetPositionalOnEnd);

        this.distance = distance;
    }

    /**
     * Determine the final encoder distance
     */
    @Override
    protected void determineFinalEncoderDistance()
    {
        this.desiredFinalLeftEncoderDistance = this.startLeftEncoderDistance + this.distance;
        this.desiredFinalRightEncoderDistance = this.startRightEncoderDistance + this.distance;
    }
}
