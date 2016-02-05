package org.usfirst.frc.team1318.robot.DefenseArm;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.Timer;

/*
 * @Author Corbin
 * This class controlls the defense arm. It is designed to have several defined positions for the arm, the ability to move the arm
 * to the front of the robot or the back of the robot, and to correct for any error that may occur during arm movement.
 */

public class DefenseArmController implements IController
{
    // The component that will be controlled, and the driver that will do the controlling
    private final DefenseArmComponent defenseArm;
    private Driver driver;

    // The cotroller for PID and a timer for use in PID functions
    private PIDHandler pidHandler;
    private Timer timer;

    // State of PID enableization
    private boolean usePID;

    // Current function the arm may be performing
    private boolean movingToFront;
    private boolean movingToBack;

    // Important values for PID functions
    private double baseState;
    private double desiredPosition;
    private double startTime;

    // Constructor should initialize all of the necessary variables for the controller, and set basic values
    public DefenseArmController(DefenseArmComponent defenseArmComponent)
    {
        this.defenseArm = defenseArmComponent;

        usePID = true;
        createPIDHandler();

        baseState = HardwareConstants.DEFENSE_ARM_BASE_STATE;
        desiredPosition = HardwareConstants.DEFENSE_ARM_BASE_STATE;

        timer = new Timer();
        startTime = timer.get();

    }

    @Override
    public void update()
    {
        // Set the current time using the timer
        double currentTime = timer.get();
        
        // Booleans to make sure that the arm doesn't break itself against its boundaries
        boolean enforceNonNegative = false;
        boolean enforceNonPositive = false;

        // Set current distance of the encoder, and create zeroOffset and motorValue
        double currentEncoderDistance = defenseArm.getEncoderTicks();
        double zeroOffset;
        double motorValue;

        // Get the values of the front and back limit switches
        boolean isAtFront = defenseArm.getFrontLimitSwitch();
        boolean isAtBack = defenseArm.getBackLimitSwitch();

        movingToFront = movingToFront || driver.getDigital(Operation.DefenseArmForward);
        movingToBack = movingToBack || driver.getDigital(Operation.DefenseArmBack);

        // 
        if (movingToFront)
        {
            desiredPosition = TuningConstants.DEFENSE_ARM_PAST_FRONT_POSITION;
        }

        else if (movingToBack)
        {
            desiredPosition = TuningConstants.DEFENSE_ARM_PAST_BACK_POSITION;
        }

        // Check to see if the arm is at the front of the robot, 
        // update zeroOffset, and set the appropirate boolean to false
        if (isAtFront)
        {
            defenseArm.setZeroOffset(currentEncoderDistance);
            movingToFront = false;

            enforceNonNegative = true;
        }

        // Check to see if the arm is at the back of the robot, 
        // and set the appropirate boolean to false
        if (isAtBack)
        {
            movingToBack = false;
            
            enforceNonPositive = true;
        }

        // Makes the base state of the defense arm either the front or their back on command
        if (driver.getDigital(Operation.DefenseArmFrontState))
        {
            baseState = HardwareConstants.DEFENSE_ARM_FRONT_STATE;
        }
        else if (driver.getDigital(Operation.DefenseArmBackState))
        {
            baseState = HardwareConstants.DEFENSE_ARM_BACK_STATE;
        }

        // Sets the desiredPosition based on several possible inputs
        if (driver.getDigital(Operation.DefenseArmMoveToPosition1))
        {
            desiredPosition = baseState + HardwareConstants.DEFENSE_ARM_POSITION_1;
            movingToFront = false;
            movingToBack = false;
        }
        else if (driver.getDigital(Operation.DefenseArmMoveToPosition2))
        {
            desiredPosition = baseState + HardwareConstants.DEFENSE_ARM_POSITION_2;
            movingToFront = false;
            movingToBack = false;
        }
        else if (driver.getDigital(Operation.DefenseArmMoveToPosition3))
        {
            desiredPosition = baseState + HardwareConstants.DEFENSE_ARM_POSITION_3;
            movingToFront = false;
            movingToBack = false;
        }
        else if (driver.getDigital(Operation.DefenseArmMoveToPosition4))
        {
            desiredPosition = baseState + HardwareConstants.DEFENSE_ARM_POSITION_4;
            movingToFront = false;
            movingToBack = false;
        }
        else if (driver.getDigital(Operation.DefenseArmMoveToPosition5))
        {
            desiredPosition = baseState + HardwareConstants.DEFENSE_ARM_POSITION_5;
            movingToFront = false;
            movingToBack = false;
        }

        zeroOffset = defenseArm.getZeroOffset();

        // Logic for moving the defense arm forward and backward manually
        if (usePID)
        {
            if (movingToBack)
            {
                desiredPosition = currentEncoderDistance - zeroOffset;
                desiredPosition += TuningConstants.DEFENSE_ARM_MAX_VELOCITY * (currentTime - startTime);
                movingToFront = false;
                movingToBack = false;
            }
            else if (movingToFront)
            {
                desiredPosition = currentEncoderDistance - zeroOffset;
                desiredPosition -= TuningConstants.DEFENSE_ARM_MAX_VELOCITY * (currentTime - startTime);
                movingToFront = false;
                movingToBack = false;
            }
            motorValue = pidHandler.calculatePosition(zeroOffset + desiredPosition, defenseArm.getEncoderTicks());
        }
        else
        {
            if (driver.getDigital(Operation.DefenseArmMoveToBack))
            {
                motorValue = TuningConstants.DEFENSE_ARM_OVERRIDE_POWER_LEVEL;
                movingToFront = false;
                movingToBack = false;
            }
            else if (this.driver.getDigital(Operation.DefenseArmMoveToFront))
            {
                motorValue = -TuningConstants.DEFENSE_ARM_OVERRIDE_POWER_LEVEL;
                movingToFront = false;
                movingToBack = false;
            }
            else
            {
                motorValue = 0.0;
                movingToFront = false;
                movingToBack = false;
            }
        }
        
        if (enforceNonNegative || enforceNonPositive)
        {
            defenseArm.setSpeed(0.0);
        }
        else 
        {
            defenseArm.setSpeed(motorValue);
        }

    }

    @Override
    public void stop()
    {
        defenseArm.setSpeed(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    // Initialization method for the PID handler, with built in check for PID toggle
    private void createPIDHandler()
    {
        // Initial check if using PID is not desired (initializes the object if true)
        if (usePID)
        {
            pidHandler = new PIDHandler(
                "arm",
                TuningConstants.DEFENSE_ARM_POSITION_PID_KP_DEFAULT,
                TuningConstants.DEFENSE_ARM_POSITION_PID_KI_DEFAULT,
                TuningConstants.DEFENSE_ARM_POSITION_PID_KD_DEFAULT,
                TuningConstants.DEFENSE_ARM_POSITION_PID_KF_DEFAULT,
                -TuningConstants.DEFENSE_ARM_MAX_POWER_LEVEL,
                TuningConstants.DEFENSE_ARM_MAX_POWER_LEVEL);
        }
        else
        {
            pidHandler = null;
        }
    }
}
