package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import edu.wpi.first.wpilibj.Timer;

/**
 * The first half of the drawbridge macro. Should back the robot up until the defense arm is horizontal. 
 * After it is, the triangle relationship will reflect switch, and theta will need to be negated for all additional movement. 
 * The logic gets a little funky, but should correctly switch to negate mode if the the angle is zero (or very close to it). 
 * Please note that the margin for "close to zero" needs to be tuned! (I picked PI/32 radians as a starting point)
 * @author Corbin_Modica
 *
 */

public class BreachDrawbridgeTask extends ControlTaskBase
{
    private double startDTDistanceRight;
    private double startDTDistanceLeft;

    private double desiredDTDistanceRight;
    private double desiredDTDistanceLeft;

    private double currentDTDistanceRight;
    private double currentDTDistanceLeft;

    private DriveTrainComponent driveTrain;
    private double prevTime;
    private Timer timer;
    
    private boolean negateAngles;
    
    public BreachDrawbridgeTask()
    {
    }
    
    @Override
    public void begin()
    {
        this.timer = new Timer();
        this.timer.start();
        this.prevTime = timer.get();
        
        this.driveTrain = this.getComponents().getDriveTrain();
        
        this.startDTDistanceLeft = this.getComponents().getDriveTrain().getLeftEncoderDistance();
        this.startDTDistanceRight = this.getComponents().getDriveTrain().getRightEncoderDistance();
        
        this.desiredDTDistanceLeft = this.startDTDistanceLeft - TuningConstants.DRAWBRIDGE_BACKUP_DISTANCE;
        this.desiredDTDistanceRight = this.startDTDistanceRight - TuningConstants.DRAWBRIDGE_BACKUP_DISTANCE;
        
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, true);
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        
        this.negateAngles = false;
    }

    @Override
    public void update()
    {
        double currTime = this.timer.get();
        double timeElapsed = currTime - this.prevTime;
        this.prevTime = currTime;
        
        this.currentDTDistanceLeft = this.driveTrain.getLeftEncoderDistance();
        this.currentDTDistanceRight = this.driveTrain.getRightEncoderDistance();
        
        double desiredDistanceChange = TuningConstants.DRAWBRIDGE_BREACH_VELOCITY * timeElapsed;
        
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.currentDTDistanceRight + desiredDistanceChange);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.currentDTDistanceLeft + desiredDistanceChange);
        
        double totalCurrentDistance = (this.currentDTDistanceLeft + this.currentDTDistanceRight)/2;
        
        double angle = Math.acos((totalCurrentDistance + desiredDistanceChange)/(HardwareConstants.DEFENSE_ARM_LENGTH + HardwareConstants.DEFENSE_ARM_DRAWBRIDGE_EXTENSION_LENGTH));
        
        // If it need be negative, set the angle to negative.
        if (this.negateAngles)
        {
            angle *= -1;
        }
        
        // Check to see if the angle is zero (or very close to, as we may not hit zero every time), and if it is, make it so that the angle calculated next cycle will be negative.
        if (0.0 <= angle && angle <= (Math.PI/32))
        {
            this.negateAngles = true;
        } 
        
        // Set the angle using the calculated value, and adjusting for horizontal being 0 radians (instead of the lower limit of the defense arm).
        this.setAnalogOperationState(Operation.DefenseArmSetAngle, angle * HardwareConstants.DEFENSE_ARM_TICKS_PER_RADIAN + HardwareConstants.DEFENSE_ARM_THETA_OFFSET);
    }

    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);   
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);   
    }

    @Override
    public boolean hasCompleted()
    {
        // Return true if the desired position has been reached.
        if (this.desiredDTDistanceLeft <= this.currentDTDistanceLeft && this.desiredDTDistanceRight <= this.currentDTDistanceRight)
        {
            return true;
        }
        // Return true if theta is 0 (and start the second part of the macro in this case).
        else
        {
            return false;
        }
    }
    

}
