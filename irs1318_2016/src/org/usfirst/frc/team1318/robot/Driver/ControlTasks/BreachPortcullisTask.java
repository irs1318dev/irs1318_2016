package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;

/**
 * @author Preston and Corbin
 * This class is a task designed to automatically drive the robot through the portcullis.
 */
public class BreachPortcullisTask extends CoordinatedDriveDefenseArmTaskBase
{
    public BreachPortcullisTask()
    {
        super(TuningConstants.PORTCULLIS_BREACH_DISTANCE, TuningConstants.PORTCULLIS_BREACH_VELOCITY);
    }

    protected double calculateArmAngle(double traveledDistanceRatio)
    {
        final double startingAngle = TuningConstants.DEFENSE_ARM_PORTCULLIS_BREACH_CAPTURE_POSITION;

        // Find the desired Arm Angle in radians
        return startingAngle + traveledDistanceRatio * (Math.PI - startingAngle);
    }
}
