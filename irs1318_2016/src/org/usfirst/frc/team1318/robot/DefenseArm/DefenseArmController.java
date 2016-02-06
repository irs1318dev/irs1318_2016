package org.usfirst.frc.team1318.robot.DefenseArm;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Common.PIDHandler;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.Timer;

/**
 * Controller for the defense arm. It is designed to have several defined positions for the arm, the ability to move the arm
 * to the front of the robot or the back of the robot, have several set states for the bottom
 * and to correct for any error that may occur during arm movement.
 * @author Corbin
 *
 */
public class DefenseArmController implements IController
{
    // The component that will be controlled, and the driver that will do the controlling
    private final DefenseArmComponent defenseArm;
    private Driver driver;

    // The controller for PID and a timer for use in PID functions
    private PIDHandler pidHandler;
    private Timer timer;

    // State of PID (enabled or not)
    private boolean usePID;

    // Current function the arm may be performing
    private boolean movingToFront;
    private boolean movingToBack;

    // Important values for PID functions
    private double desiredPosition;
    private double startTime;

    // Constructor should initialize all of the necessary variables for the controller, and set basic values
    public DefenseArmController(DefenseArmComponent defenseArmComponent)
    {
        this.defenseArm = defenseArmComponent;

        this.usePID = true;
        this.createPIDHandler();

        this.desiredPosition = HardwareConstants.DEFENSE_ARM_FRONT_POSITION;

        this.timer = new Timer();
        this.timer.start();
        this.startTime = this.timer.get();
    }

    @Override
    public void update()
    {
        // Set the current time using the timer
        double currentTime = this.timer.get();

        // Booleans to make sure that the arm doesn't break itself against its boundaries
        boolean enforceNonNegative = false;
        boolean enforceNonPositive = false;

        // Set current distance of the encoder, and create zeroOffset and motorValue
        double currentEncoderDistance = this.defenseArm.getEncoderTicks();
        double zeroOffset;
        double motorValue;

        // Get the values of the front and back limit switches
        boolean isAtFront = this.defenseArm.getFrontLimitSwitch();
        boolean isAtBack = this.defenseArm.getBackLimitSwitch();

        // Operation check for the portcullis macro        
        if (this.driver.getDigital(Operation.DefenseArmTakePositionInput))
        {
            this.desiredPosition = this.driver.getAnalog(Operation.DefenseArmSetAngle);
        }

        // Check for the desire to move the arm to the front or back of the robot
        if (this.driver.getDigital(Operation.DefenseArmFrontPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_FRONT_POSITION;
            this.movingToFront = true;
            this.movingToBack = false;
        }
        else if (this.driver.getDigital(Operation.DefenseArmBackPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_BACK_POSITION;
            this.movingToFront = false;
            this.movingToBack = true;
        }
        else if (this.driver.getDigital(Operation.DefenseArmPortcullisPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_PORTCULLIS_POSITION;
            this.movingToFront = false;
            this.movingToBack = false;
        }
        else if (this.driver.getDigital(Operation.DefenseArmDrawbridgePosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_DRAWBRIDGE_POSITION;
            this.movingToFront = false;
            this.movingToBack = false;
        }
        else if (this.driver.getDigital(Operation.DefenseArmSallyPortPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_SALLY_PORT_POSITION;
            this.movingToFront = false;
            this.movingToBack = false;
        }

        // Check to see if the arm is at the front of the robot, 
        // update zeroOffset, and set the appropriate boolean to false
        if (isAtFront)
        {
            this.defenseArm.setZeroOffset(currentEncoderDistance);
            this.movingToFront = false;
            enforceNonNegative = true;
        }

        // Check to see if the arm is at the back of the robot, 
        // and set the appropriate boolean to false
        if (isAtBack)
        {
            this.movingToBack = false;            
            enforceNonPositive = true;
        }

        // Sets the desiredPosition based on several possible inputs
        zeroOffset = this.defenseArm.getZeroOffset();

        // Logic for moving the defense arm forward and backward manually
        if (this.usePID)
        {
            if (this.movingToFront)
            {
                motorValue = -TuningConstants.DEFENSE_ARM_MOVE_END_POWER_LEVEL;
            }
            else if (this.movingToBack)
            {
                motorValue = TuningConstants.DEFENSE_ARM_MOVE_END_POWER_LEVEL;
            }
            else
            {
                if (this.driver.getDigital(Operation.DefenseArmMoveForward))
                {
                    this.desiredPosition = currentEncoderDistance - zeroOffset;
                    this.desiredPosition -= TuningConstants.DEFENSE_ARM_MAX_VELOCITY * (currentTime - this.startTime);
                    this.movingToFront = false;
                    this.movingToBack = false;
                }
                else if (this.driver.getDigital(Operation.DefenseArmMoveBack))
                {
                    this.desiredPosition = currentEncoderDistance - zeroOffset;
                    this.desiredPosition += TuningConstants.DEFENSE_ARM_MAX_VELOCITY * (currentTime - this.startTime);
                    this.movingToFront = false;
                    this.movingToBack = false;
                }

                motorValue = this.pidHandler.calculatePosition(zeroOffset + this.desiredPosition, this.defenseArm.getEncoderTicks());
            }
        }
        else
        {
            if (this.driver.getDigital(Operation.DefenseArmMoveForward))
            {
                motorValue = -TuningConstants.DEFENSE_ARM_OVERRIDE_POWER_LEVEL;
            }
            else if (this.driver.getDigital(Operation.DefenseArmMoveBack))
            {
                motorValue = TuningConstants.DEFENSE_ARM_OVERRIDE_POWER_LEVEL;
            }
            else
            {
                motorValue = 0.0;
            }

            this.movingToFront = false;
            this.movingToBack = false;
        }

        if (enforceNonNegative)
        {
            motorValue = Math.min(0.0, motorValue);
        }

        if (enforceNonPositive)
        {
            motorValue = Math.max(0.0, motorValue);
        }

        this.defenseArm.setSpeed(motorValue);
    }

    @Override
    public void stop()
    {
        this.defenseArm.setSpeed(0.0);
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
        if (this.usePID)
        {
            this.pidHandler = new PIDHandler(
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
            this.pidHandler = null;
        }
    }
}
