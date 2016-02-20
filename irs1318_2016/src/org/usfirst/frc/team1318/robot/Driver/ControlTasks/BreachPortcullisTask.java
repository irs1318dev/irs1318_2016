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
        // Find the desired Arm Angle in radians
        return Math.acos(1.5 * traveledDistanceRatio - 0.5);
    }
}
