package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.common.Helpers;
import frc.robot.common.IMechanism;
import frc.robot.common.PIDHandler;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IEncoder;
import frc.robot.common.robotprovider.IMotor;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

/**
 * Drivetrain mechanism.
 * The mechanism defines the logic that controls a mechanism given inputs and operator-requested actions, and 
 * translates those into the abstract functions that should be applied to the outputs.
 * 
 */
@Singleton
public class DriveTrainMechanism implements IMechanism
{
    private static final String LogName = "dt";

    private static final int pidSlotId = 0;
    private static final int FRAME_PERIOD_MS = 5;

    private static final double POWERLEVEL_MIN = -1.0;
    private static final double POWERLEVEL_MAX = 1.0;

    private final IDashboardLogger logger;
    private final ITimer timer;

    private final IMotor leftMotor;
    private final IMotor rightMotor;

    private final IEncoder leftEncoder;
    private final IEncoder rightEncoder;

    private Driver driver;

    private PIDHandler leftPID;
    private PIDHandler rightPID;

    private boolean usePID;
    private boolean usePositionalMode;
    private boolean useBrakeMode;

    private double leftVelocity;
    private double leftDistance;
    private int leftPosition;
    private double rightVelocity;
    private double rightDistance;
    private int rightPosition;

    /**
     * Initializes a new DriveTrainMechanism
     * @param logger to use
     * @param provider for obtaining electronics objects
     * @param timer to use
     */
    @Inject
    public DriveTrainMechanism(
        IDashboardLogger logger,
        IRobotProvider provider,
        ITimer timer)
    {
        this.logger = logger;
        this.timer = timer;

        this.leftMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_LEFT_TALON_CHANNEL);

        this.rightMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_RIGHT_TALON_CHANNEL);

        this.leftEncoder = provider.getEncoder(
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);

        this.rightEncoder = provider.getEncoder(
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A,
            ElectronicsConstants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);

        this.leftEncoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE);
        this.rightEncoder.setDistancePerPulse(HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE);

        this.leftPID = null;
        this.rightPID = null;

        this.usePID = TuningConstants.DRIVETRAIN_USE_PID_DEFAULT;
        this.usePositionalMode = false;
        this.useBrakeMode = false;

        this.leftVelocity = 0.0;
        this.leftDistance = 0.0;
        this.leftPosition = 0;
        this.rightVelocity = 0.0;
        this.rightDistance = 0.0;
        this.rightPosition = 0;
    }

    /**
     * get the velocity from the left encoder
     * @return a value indicating the velocity
     */
    public double getLeftVelocity()
    {
        return this.leftVelocity;
    }

    /**
     * get the velocity from the right encoder
     * @return a value indicating the velocity
     */
    public double getRightVelocity()
    {
        return this.rightVelocity;
    }

    /**
     * get the distance from the left encoder
     * @return a value indicating the distance
     */
    public double getLeftDistance()
    {
        return this.leftDistance;
    }

    /**
     * get the distance from the right encoder
     * @return a value indicating the distance
     */
    public double getRightDistance()
    {
        return this.rightDistance;
    }

    /**
     * get the ticks from the left encoder
     * @return a value indicating the number of ticks we are at
     */
    public int getLeftPosition()
    {
        return this.leftPosition;
    }

    /**
     * get the ticks from the right encoder
     * @return a value indicating the number of ticks we are at
     */
    public int getRightPosition()
    {
        return this.rightPosition;
    }

    /**
     * set the driver that the mechanism should use
     * @param driver to use
     */
    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;

        // switch to default velocity PID mode whenever we switch drivers (defense-in-depth)
        if (!this.usePID || this.usePositionalMode || this.useBrakeMode)
        {
            this.usePID = TuningConstants.DRIVETRAIN_USE_PID_DEFAULT;
            this.usePositionalMode = false;
            this.useBrakeMode = false;
        }

        this.createPIDHandler();
    }

    /**
     * read all of the sensors for the mechanism that we will use in macros/autonomous mode and record their values
     */
    @Override
    public void readSensors()
    {
        this.leftVelocity = -this.leftEncoder.getRate();
        this.rightVelocity = this.rightEncoder.getRate();

        this.leftDistance = -this.leftEncoder.getDistance();
        this.rightDistance = this.rightEncoder.getDistance();

        this.leftPosition = this.leftEncoder.get();
        this.rightPosition = this.rightEncoder.get();

        this.logger.logNumber(DriveTrainMechanism.LogName, "leftVelocity", this.leftVelocity);
        this.logger.logNumber(DriveTrainMechanism.LogName, "leftDistance", this.leftDistance);
        this.logger.logNumber(DriveTrainMechanism.LogName, "leftTicks", this.leftPosition);
        this.logger.logNumber(DriveTrainMechanism.LogName, "rightVelocity", this.rightVelocity);
        this.logger.logNumber(DriveTrainMechanism.LogName, "rightDistance", this.rightDistance);
        this.logger.logNumber(DriveTrainMechanism.LogName, "rightTicks", this.rightPosition);
    }

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant mechanism
     */
    @Override
    public void update()
    {
        if (this.driver.getDigital(DigitalOperation.EnablePID))
        {
            this.usePID = true;
            this.createPIDHandler();
        }
        else if (this.driver.getDigital(DigitalOperation.DisablePID))
        {
            this.usePID = false;
            this.createPIDHandler();
        }

        // check our desired PID mode (needed for positional mode or break mode)
        boolean newUsePositionalMode = this.driver.getDigital(DigitalOperation.DriveTrainUsePositionalMode);
        boolean newUseBrakeMode = this.driver.getDigital(DigitalOperation.DriveTrainUseBrakeMode);
        if (newUsePositionalMode != this.usePositionalMode ||
            newUseBrakeMode != this.useBrakeMode)
        {
            this.usePositionalMode = newUsePositionalMode;
            this.useBrakeMode = newUseBrakeMode;

            // re-create PID handler
            this.createPIDHandler();
        }

        // calculate desired setting for the current mode
        Setpoint setpoint;
        if (this.usePositionalMode)
        {
            setpoint = this.calculatePositionModeSetpoint();
        }
        else
        {
            setpoint = this.calculateVelocityModeSetpoint();
        }

        double leftSetpoint = setpoint.getLeft();
        double rightSetpoint = setpoint.getRight();

        this.logger.logNumber(DriveTrainMechanism.LogName, "leftVelocityGoal", leftSetpoint);
        this.logger.logNumber(DriveTrainMechanism.LogName, "rightVelocityGoal", rightSetpoint);

        if (leftSetpoint > 0)
        {
            leftSetpoint /= TuningConstants.DRIVETRAIN_REVERSE_LEFT_SCALE_FACTOR;
        }

        if (rightSetpoint > 0)
        {
            rightSetpoint /= TuningConstants.DRIVETRAIN_REVERSE_RIGHT_SCALE_FACTOR;
        }

        // apply the setpoints to the motors
        this.leftMotor.set(leftSetpoint);
        this.rightMotor.set(rightSetpoint);
    }

    /**
     * stop the relevant mechanism
     */
    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);

        this.leftEncoder.reset();
        this.rightEncoder.reset();

        if (this.leftPID != null)
        {
            this.leftPID.reset();
        }

        if (this.rightPID != null)
        {
            this.rightPID.reset();
        }

        this.leftVelocity = 0.0;
        this.leftDistance = 0.0;
        this.leftPosition = 0;
        this.rightVelocity = 0.0;
        this.rightDistance = 0.0;
        this.rightPosition = 0;
    }

    /**
     * create a PIDHandler based on our current settings
     */
    private void createPIDHandler()
    {
        if (!this.usePID)
        {
            this.leftPID = null;
            this.rightPID = null;
        }
        else
        {
            if (this.usePositionalMode)
            {
                this.leftPID = new PIDHandler(
                    TuningConstants.DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_LEFT_KD_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_LEFT_KF_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_LEFT_KS_DEFAULT,
                    -TuningConstants.DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL,
                    TuningConstants.DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL,
                    this.timer);

                this.rightPID = new PIDHandler(
                    TuningConstants.DRIVETRAIN_POSITION_PID_RIGHT_KP_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_RIGHT_KI_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_RIGHT_KD_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_RIGHT_KF_DEFAULT,
                    TuningConstants.DRIVETRAIN_POSITION_PID_RIGHT_KS_DEFAULT,
                    -TuningConstants.DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL,
                    TuningConstants.DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL,
                    this.timer);
            }
            else
            {
                this.leftPID = new PIDHandler(
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KP_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KI_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KD_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KF_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_LEFT_KS_DEFAULT,
                    -TuningConstants.DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL,
                    TuningConstants.DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL,
                    this.timer);

                this.rightPID = new PIDHandler(
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KP_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KI_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KD_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KF_DEFAULT,
                    TuningConstants.DRIVETRAIN_VELOCITY_PID_RIGHT_KS_DEFAULT,
                    -TuningConstants.DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL,
                    TuningConstants.DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL,
                    this.timer);;
            }
        }
    }

    /**
     * Calculate the setting to use based on the inputs when in velocity mode
     * @return settings for left and right motor
     */
    private Setpoint calculateVelocityModeSetpoint()
    {
        // velocity goals represent the desired percentage of the max velocity
        double leftVelocityGoal = 0.0;
        double rightVelocityGoal = 0.0;

        // get a value indicating that we should be in simple mode...
        boolean simpleDriveModeEnabled = this.driver.getDigital(DigitalOperation.DriveTrainSimpleMode);

        // get the X and Y values from the operator.  We expect these to be between -1.0 and 1.0,
        // with this value representing the forward velocity percentage and right turn percentage (of max speed)
        double turnAmount = this.driver.getAnalog(AnalogOperation.DriveTrainTurn);
        double forwardVelocity = this.driver.getAnalog(AnalogOperation.DriveTrainMoveForward);

        // Negate the x and y if DriveTrainSwapFrontOrientation is true
        if (this.driver.getDigital(DigitalOperation.DriveTrainSwapFrontOrientation))
        {
            turnAmount *= -1.0;
            forwardVelocity *= -1.0;
        }

        // adjust the intensity of the input
        if (simpleDriveModeEnabled)
        {
            if (Math.abs(forwardVelocity) < Math.abs(turnAmount))
            {
                // in-place turn
                leftVelocityGoal = turnAmount;
                rightVelocityGoal = -turnAmount;
            }
            else
            {
                // forward/backward
                leftVelocityGoal = forwardVelocity;
                rightVelocityGoal = forwardVelocity;
            }
        }
        else
        {
            leftVelocityGoal = (TuningConstants.DRIVETRAIN_K1 * forwardVelocity) + (TuningConstants.DRIVETRAIN_K2 * turnAmount);
            rightVelocityGoal = (TuningConstants.DRIVETRAIN_K1 * forwardVelocity) + (-TuningConstants.DRIVETRAIN_K2 * turnAmount);
        }

        // decrease the desired velocity based on the configured max power level
        leftVelocityGoal = leftVelocityGoal * TuningConstants.DRIVETRAIN_MAX_POWER_LEVEL;
        rightVelocityGoal = rightVelocityGoal * TuningConstants.DRIVETRAIN_MAX_POWER_LEVEL;

        // convert velocity goal to power level...
        double left;
        double right;
        if (this.usePID)
        {
            left = this.leftPID.calculateVelocityByTicks(
                leftVelocityGoal,
                this.leftPosition);

            right = this.rightPID.calculateVelocityByTicks(
                rightVelocityGoal,
                this.rightPosition);
        }
        else
        {
            left = leftVelocityGoal;
            right = rightVelocityGoal;
        }
        
        // ensure that we don't give values outside the appropriate range
        left = this.applyPowerLevelRange(leftVelocityGoal);
        right = this.applyPowerLevelRange(rightVelocityGoal);

        return new Setpoint(left, right);
    }

    /**
     * Calculate the setting to use based on the inputs when in position mode
     * @return settings for left and right motor
     */
    private Setpoint calculatePositionModeSetpoint()
    {
        // get the desired left and right values from the driver.
        double leftPositionGoal = this.driver.getAnalog(AnalogOperation.DriveTrainLeftPosition);
        double rightPositionGoal = this.driver.getAnalog(AnalogOperation.DriveTrainRightPosition);

        this.logger.logNumber(DriveTrainMechanism.LogName, "leftPositionGoal", leftPositionGoal);
        this.logger.logNumber(DriveTrainMechanism.LogName, "rightPositionGoal", rightPositionGoal);

        double leftPower;
        double rightPower;
        if (this.usePID)
        {
            // use positional PID to get the relevant value
            leftPower = this.leftPID.calculatePosition(leftPositionGoal, this.leftPosition);
            rightPower = this.rightPID.calculatePosition(rightPositionGoal, this.rightPosition);
        }
        else
        {
            // calculate a desired power level
            leftPower = leftPositionGoal - this.leftPosition;
            rightPower = rightPositionGoal - this.rightPosition;
            if (Math.abs(leftPower) < 0.1)
            {
                leftPower = 0.0;
            }

            if (Math.abs(rightPower) < 0.1)
            {
                rightPower = 0.0;
            }

            leftPower *= TuningConstants.DRIVETRAIN_LEFT_POSITIONAL_NON_PID_MULTIPLICAND;
            rightPower *= TuningConstants.DRIVETRAIN_RIGHT_POSITIONAL_NON_PID_MULTIPLICAND;

            // ensure that we are within our power level range, and then scale it down
            leftPower = this.applyPowerLevelRange(leftPower) * TuningConstants.DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID;
            rightPower = this.applyPowerLevelRange(rightPower) * TuningConstants.DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID;
        }

        this.assertPowerLevelRange(leftPower, "left velocity (goal)");
        this.assertPowerLevelRange(rightPower, "right velocity (goal)");

        return new Setpoint(leftPower, rightPower);
    }

    /**
     * Assert that the power level is within the required range
     * @param powerLevel to verify
     * @param side indicator for the exception message if incorrect
     */
    private void assertPowerLevelRange(double powerLevel, String side)
    {
        if (powerLevel < DriveTrainMechanism.POWERLEVEL_MIN)
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(side + " power level too low!");
            }

            return;
        }

        if (powerLevel > DriveTrainMechanism.POWERLEVEL_MAX)
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(side + " power level too high!");
            }

            return;
        }
    }

    /**
     * Reset the power level to be within the required range
     * @param powerLevel to reset
     * @return power level
     */
    private double applyPowerLevelRange(double powerLevel)
    {
        return Helpers.EnforceRange(powerLevel, DriveTrainMechanism.POWERLEVEL_MIN, DriveTrainMechanism.POWERLEVEL_MAX);
    }

    /**
     * Simple holder of setpoint information for the left and right sides
     */
    private class Setpoint
    {
        private double left;
        private double right;

        /**
         * Initializes a new Setpoint
         * @param left value to apply
         * @param right value to apply
         */
        public Setpoint(double left, double right)
        {
            this.left = left;
            this.right = right;
        }

        /**
         * gets the left setpoint
         * @return left setpoint value
         */
        public double getLeft()
        {
            return this.left;
        }

        /**
         * gets the right setpoint
         * @return right setpoint value
         */
        public double getRight()
        {
            return this.right;
        }
    }
}
