package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class SallyPortArcDriveTask extends ControlTaskBase
{
    private double startDTDistanceRight;
    private double startDTDistanceLeft;

    private double desiredDTDistanceRight;
    private double desiredDTDistanceLeft;

    private double currentDTDistanceRight;
    private double currentDTDistanceLeft;

    public SallyPortArcDriveTask()
    {    
    }

    @Override
    public void begin()
    {   
        final DriveTrainComponent driveTrain = this.getComponents().getDriveTrain();
        // Log the starting distance of the encoders (for the drive train)
        this.startDTDistanceRight = driveTrain.getRightEncoderDistance();
        this.startDTDistanceLeft = driveTrain.getLeftEncoderDistance();

        // Calculate the desired position for the left and right sides of the drive train (dependant upon their arc distances)
        this.desiredDTDistanceRight = this.startDTDistanceRight + TuningConstants.SALLY_PORT_ARC_DRIVE_DISTANCE_PART_TWO_RIGHT;
        this.desiredDTDistanceLeft = this.startDTDistanceLeft + TuningConstants.SALLY_PORT_ARC_DRIVE_DISTANCE_PART_TWO_LEFT;
        
        // Set the desired positions for the left and right sides.
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.desiredDTDistanceRight);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.desiredDTDistanceLeft);
        
        // Set necessary operations to true
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);   
    }

    @Override
    public void update()
    {
        
    }

    @Override
    public void stop()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);    
    }

    @Override
    public void end()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);               
    }

    @Override
    public boolean hasCompleted()
    {
        // Check that the distance the robot has traveled (with the purpose of returning true if the desired position has been met)
        return this.currentDTDistanceRight >= this.desiredDTDistanceRight 
            && this.currentDTDistanceLeft >= this.desiredDTDistanceLeft;
    }
}
