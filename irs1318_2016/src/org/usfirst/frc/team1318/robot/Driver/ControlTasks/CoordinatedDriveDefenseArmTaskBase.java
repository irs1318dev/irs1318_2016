package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.Timer;

public abstract class CoordinatedDriveDefenseArmTaskBase extends ControlTaskBase
{
    protected double startDTDistanceRight;
    protected double startDTDistanceLeft;

    protected double desiredDTDistanceRight;
    protected double desiredDTDistanceLeft;

    protected double currentDTDistanceRight;
    protected double currentDTDistanceLeft;

    private DriveTrainComponent driveTrain;
    private double prevTime;
    private Timer timer;
    
    // class-specific variables:
    private final double driveDistance;
    private final double driveVelocity;

    protected CoordinatedDriveDefenseArmTaskBase(
        double driveDistance,
        double driveVelocity)
    {
        this.driveDistance = driveDistance;
        this.driveVelocity = driveVelocity;
    }

    @Override
    public void begin()
    {
        this.driveTrain = this.getComponents().getDriveTrain();
        this.timer = new Timer();
        this.timer.start();
        this.prevTime = this.timer.get();
        
        // Log the starting distance of the encoders (for the drive train)
        this.startDTDistanceRight = this.driveTrain.getRightEncoderDistance();
        this.startDTDistanceLeft = this.driveTrain.getLeftEncoderDistance();

        // Set the desired position for the drive train
        this.desiredDTDistanceRight = this.startDTDistanceRight + this.driveDistance;
        this.desiredDTDistanceLeft = this.startDTDistanceLeft + this.driveDistance;
        
        // Set necessary operations to true
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, true);
    }

    @Override
    public void update()
    {
        // Find current encoder distances
        this.currentDTDistanceRight = this.driveTrain.getRightEncoderDistance();
        this.currentDTDistanceLeft = this.driveTrain.getLeftEncoderDistance();

        // calculate the elapsed time
        double currTime = this.timer.get();
        double timeElapsed = currTime - this.prevTime;
        this.prevTime = currTime;

        // Move the drive train by the velocity constant
        double distanceToTravel = this.driveVelocity * timeElapsed;
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.currentDTDistanceRight
            + distanceToTravel);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.currentDTDistanceLeft
            + distanceToTravel);

        // Find distance traveled by both right and left wheels since macro started
        double traveledRightDistance = this.currentDTDistanceRight - this.startDTDistanceRight;
        double traveledLeftDistance = this.currentDTDistanceLeft - this.startDTDistanceLeft;

        // Find average of right and left traveled distance (both left and right should be the same theoretically);
        double traveledDistance = (traveledRightDistance + traveledLeftDistance) / 2;

        // determine what ratio compared to the arm length we have traveled.  If we are before our starting location
        double traveledDistanceRatio = traveledDistance / this.driveDistance;
        if (traveledDistanceRatio < 0.0)
        {
            traveledDistanceRatio = 0.0;
        }
        else if (traveledDistanceRatio > 1.0)
        {
            traveledDistanceRatio = 1.0;
        }

        // Find the desired Arm Angle in radians
        double armAngle = this.calculateArmAngle(traveledDistanceRatio);

        if (Double.isNaN(armAngle))
        {
            throw new RuntimeException("don't expect NaN angle!");
        }

        // Set the desired arm angle converted to ticks
        this.setAnalogOperationState(Operation.DefenseArmSetAngle, armAngle);
    }

    @Override
    public void stop()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
    }

    @Override
    public void end()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
    }

    @Override
    public boolean hasCompleted()
    {
        // Check that the distance the robot has traveled (with the purpose of returning true if the desired position has been met)
        return this.currentDTDistanceRight >= this.desiredDTDistanceRight && this.currentDTDistanceLeft >= this.desiredDTDistanceLeft;
    }

    protected abstract double calculateArmAngle(double traveledDistanceRatio);
}
