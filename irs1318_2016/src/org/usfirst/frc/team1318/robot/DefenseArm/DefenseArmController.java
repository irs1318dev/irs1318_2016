package org.usfirst.frc.team1318.robot.DefenseArm;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
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

    // State of PID/Sensors (enabled or not)
    private boolean usePID;
    private boolean useSensors;

    // Current function the arm may be performing
    private boolean movingToFront;
    private boolean movingToBack;

    // Important values for PID functions
    private double desiredPosition;
    private double prevTime;

    // Constructor should initialize all of the necessary variables for the controller, and set basic values
    public DefenseArmController(DefenseArmComponent defenseArmComponent)
    {
        this.defenseArm = defenseArmComponent;

        this.usePID = true;
        this.createPIDHandler();

        this.useSensors = TuningConstants.DEFENSE_ARM_USE_SENSORS_DEFAULT;

        this.desiredPosition = TuningConstants.DEFENSE_ARM_STARTING_POSITION_DEFAULT;

        this.timer = new Timer();
        this.timer.start();
        this.prevTime = this.timer.get();
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(Operation.EnablePID))
        {
            this.usePID = true;
            this.createPIDHandler();
        }
        else if (this.driver.getDigital(Operation.DisablePID))
        {
            this.usePID = false;
            this.createPIDHandler();
        }

        if (this.driver.getDigital(Operation.DefenseArmIgnoreSensors))
        {
            this.useSensors = false;
        }
        else if (this.driver.getDigital(Operation.DefenseArmUseSensors))
        {
            this.useSensors = true;
        }

        // Set the current time using the timer
        double currentTime = this.timer.get();
        double deltaT = currentTime - this.prevTime;

        // Booleans to make sure that the arm doesn't break itself against its boundaries
        boolean enforceNonNegative = false;
        boolean enforceNonPositive = false;

        // Set current distance of the encoder, and create frontOffset and motorValue
        this.defenseArm.getEncoderTicks();
        double currentEncoderAngle = this.defenseArm.getEncoderAngle();
        double frontOffset;
        double motorValue = 0.0;

        // Get the values of the front and back limit switches
        boolean isAtFront = this.defenseArm.getFrontLimitSwitch();
        boolean isAtBack = this.defenseArm.getBackLimitSwitch();

        if (this.useSensors)
        {
            // Check to see if the arm is at the front of the robot, 
            // update front offset, and set the appropriate boolean to false
            if (isAtFront)
            {
                this.desiredPosition = HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION;
                this.defenseArm.setAbsoluteFrontOffset(currentEncoderAngle - this.desiredPosition);
                this.movingToFront = false;
                enforceNonNegative = true;
            }

            // Check to see if the arm is at the back of the robot, 
            // and set the appropriate boolean to false
            if (isAtBack)
            {
                this.desiredPosition = HardwareConstants.DEFENSE_ARM_MAX_BACK_POSITION;
                this.defenseArm.setAbsoluteFrontOffset(currentEncoderAngle - this.desiredPosition);
                this.movingToBack = false;
                enforceNonPositive = true;
            }
        }

        // Operation check for the portcullis macro
        if (this.driver.getDigital(Operation.DefenseArmTakePositionInput))
        {
            this.desiredPosition = this.assertDesiredPositionRange(
                this.driver.getAnalog(Operation.DefenseArmSetAngle));
        }

        // Check for the desire to move the arm to the front or back of the robot
        if (this.driver.getDigital(Operation.DefenseArmMaxFrontPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION;
            this.movingToFront = true;
            this.movingToBack = false;
        }
        else if (this.driver.getDigital(Operation.DefenseArmMaxBackPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_MAX_BACK_POSITION;
            this.movingToFront = false;
            this.movingToBack = true;
        }
        else if (this.driver.getDigital(Operation.DefenseArmHorizontalFrontPosition))
        {
            this.desiredPosition = HardwareConstants.DEFENSE_ARM_HORIZONTAL_FRONT_POSITION;
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

        // If we are ignoring the sensors, ignore movingToFront/movingToBack, and safety enforcement...
        if (!this.useSensors)
        {
            this.movingToFront = false;
            this.movingToBack = false;
        }

        // determine the front offset based on the absolute front's encoder value
        frontOffset = this.defenseArm.getAbsoluteFrontOffset();

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
                    double adjustmentAmount = -1.0 * TuningConstants.DEFENSE_ARM_MAX_VELOCITY * deltaT;
                    if (isAtBack)
                    {
                        adjustmentAmount *= 4.0;
                    }

                    this.desiredPosition += adjustmentAmount;
                    this.movingToFront = false;
                    this.movingToBack = false;
                }
                else if (this.driver.getDigital(Operation.DefenseArmMoveBack))
                {
                    double adjustmentAmount = TuningConstants.DEFENSE_ARM_MAX_VELOCITY * deltaT;
                    if (isAtFront)
                    {
                        adjustmentAmount *= 4.0;
                    }

                    this.desiredPosition += adjustmentAmount;
                    this.movingToFront = false;
                    this.movingToBack = false;
                }

                this.desiredPosition = this.assertDesiredPositionRange(this.desiredPosition);
                motorValue = this.pidHandler.calculatePosition(frontOffset + this.desiredPosition, currentEncoderAngle);
            }

            DashboardLogger.putDouble("battle_axe desiredPosition", this.desiredPosition);
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

        if (enforceNonNegative && motorValue < 0.0)
        {
            motorValue = 0.0;
        }

        if (enforceNonPositive && motorValue > 0.0)
        {
            motorValue = 0.0;
        }

        DashboardLogger.putDouble("battle_axe motorValue", motorValue);
        this.defenseArm.setSpeed(motorValue);

        this.prevTime = currentTime;
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

    private double assertDesiredPositionRange(double position)
    {
        if (position < HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION)
        {
            return HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION;
        }
        else if (position > HardwareConstants.DEFENSE_ARM_MAX_BACK_POSITION)
        {
            return HardwareConstants.DEFENSE_ARM_MAX_BACK_POSITION;
        }
        else if (Double.isNaN(position))
        {
            return 0.0;
        }
        else
        {
            return position;
        }
    }
}
