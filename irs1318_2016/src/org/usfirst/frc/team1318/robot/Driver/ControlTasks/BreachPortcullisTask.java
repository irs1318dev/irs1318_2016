package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class BreachPortcullisTask extends ControlTaskBase
{
    private double startDTDistanceRight;
    private double startDTDistanceLeft;

    private double desiredDTDistanceRight;
    private double desiredDTDistanceLeft;

    private double currentDTDistanceRight;
    private double currentDTDistanceLeft;

    private DriveTrainComponent driveTrain;

    public BreachPortcullisTask()
    {
        driveTrain = getComponents().getDriveTrain();

        startDTDistanceRight = driveTrain.getRightEncoderDistance();
        startDTDistanceLeft = driveTrain.getLeftEncoderDistance();

        desiredDTDistanceRight = startDTDistanceRight + TuningConstants.PORTCULLIS_BREACH_DISTANCE;
        desiredDTDistanceLeft = startDTDistanceLeft + TuningConstants.PORTCULLIS_BREACH_DISTANCE;

    }

    @Override
    public void begin()
    {
        // TODO Auto-generated method stub
        setDigitalOperationState(Operation.DefenseArmMoveToFront, true);
        setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        setDigitalOperationState(Operation.DefenseArmUsePositionalMode, true);
    }

    @Override
    public void update()
    {
        // Find current encoder distances
        currentDTDistanceRight = driveTrain.getRightEncoderDistance();
        currentDTDistanceLeft = driveTrain.getLeftEncoderDistance();
        // Move the drive train by the PORTCULLIS_BREACH_ITERATIVE constant
        setAnalogOperationState(Operation.DriveTrainRightPosition, currentDTDistanceRight
            + TuningConstants.PORTCULLIS_BREACH_ITERATIVE);
        setAnalogOperationState(Operation.DriveTrainLeftPosition, currentDTDistanceLeft
            + TuningConstants.PORTCULLIS_BREACH_ITERATIVE);
        // Find distance traveled by both right and left wheels since macro started
        double traveledRightDistance = currentDTDistanceRight - startDTDistanceRight;
        double traveledLeftDistance = currentDTDistanceLeft - startDTDistanceLeft;
        // Find average of right and left traveled distance (both left and right should be the same theoretically);
        double traveledDistance = (traveledRightDistance + traveledLeftDistance) / 2;
        
        // Find the desired Arm Angle in radians
        double armAngle = Math.acos((traveledDistance - HardwareConstants.DEFENSE_ARM_LENGTH)
            / HardwareConstants.DEFENSE_ARM_LENGTH);
        
        // Set the desired arm angle converted to ticks
        setAnalogOperationState(Operation.DefenseArmSetAngle, armAngle * TuningConstants.DEFENSE_ARM_RADIANS_TO_TICKS);

    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        setDigitalOperationState(Operation.DefenseArmUsePositionalMode, false);
    }

    @Override
    public void end()
    {
        // TODO Auto-generated method stub
        setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        setDigitalOperationState(Operation.DefenseArmUsePositionalMode, true);
        setDigitalOperationState(Operation.DefenseArmMoveToFront, true);
    }

    @Override
    public boolean hasCompleted()
    {
        // TODO Auto-generated method stub
        if (currentDTDistanceRight >= desiredDTDistanceRight && currentDTDistanceLeft >= desiredDTDistanceLeft)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
