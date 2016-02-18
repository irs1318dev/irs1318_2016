package org.usfirst.frc.team1318.robot.DefenseArm;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the defense arm mechanism.
 * @author Corbin
 *
 */
public class DefenseArmComponent
{
    // The following are the components needed to run the defense arm
    private final Talon talon;

    private final Encoder encoder;

    private final DigitalInput frontLimitSwitch;
    private final DigitalInput backLimitSwitch;

    private double zeroOffset;

    // General constructor for the component that initializes all of the necessary parts of the component, and sets the setpoint
    public DefenseArmComponent()
    {
        this.talon = new Talon(ElectronicsConstants.DEFENSE_ARM_MOTOR_CHANNEL);
        this.encoder = new Encoder(ElectronicsConstants.DEFENSE_ARM_ENCODER_CHANNEL_A, ElectronicsConstants.DEFENSE_ARM_ENCODER_CHANNEL_B);
        this.frontLimitSwitch = null;//new DigitalInput(ElectronicsConstants.DEFENSE_ARM_FRONT_LIMIT_SWITCH_CHANNEL);
        this.backLimitSwitch = null;//new DigitalInput(ElectronicsConstants.DEFENSE_ARM_BACK_LIMIT_SWITCH_CHANNEL);

        this.encoder.setDistancePerPulse(HardwareConstants.DEFENSE_ARM_PULSE_DISTANCE);
        this.zeroOffset = 0.0;
    }

    /**
     * Set speed of the motor controlling arm movement
     * @param speed of the motor, where negative is towards the front of the robot
     */
    public void setSpeed(double speed)
    {
        this.talon.set(-speed);
    }

    /**
     * Get Encoder ticks
     * @return the current count of the encoder on the Defense Arm
     */
    public int getEncoderTicks()
    {
        int ticks = -this.encoder.get();
        DashboardLogger.putInteger("battle_axe ticks", ticks);
        return ticks;
    }

    /**
     * Get Encoder angle
     * @return the current angle of the encoder on the Defense Arm
     */
    public double getEncoderAngle()
    {
        double angle = -this.encoder.getDistance();
        DashboardLogger.putDouble("battle_axe angle", angle);
        return angle;
    }

    /**
     * Get the front limit switch state
     * @return the state of the front limit switch for the Defense Arm
     */
    public boolean getFrontLimitSwitch()
    {
        if (this.frontLimitSwitch == null)
        {
            return false;
        }
        
        return this.frontLimitSwitch.get();
    }

    /**
     * Get the back limit switch state
     * @return the state of the back limit switch for the Defense Arm
     */
    public boolean getBackLimitSwitch()
    {
        if (this.backLimitSwitch == null)
        {
            return false;
        }
        
        return this.backLimitSwitch.get();
    }

    /**
     * Get the zero offset
     * @return the current count of zeroOffset
     */
    public double getZeroAngleOffset()
    {
        return this.zeroOffset;
    }

    /**
     * Set the value of zeroOffset
     * @param zeroOffset encoder position when the arm is all the way forward
     */
    public void setZeroOffset(double zeroOffset)
    {
        this.zeroOffset = zeroOffset;
    }
}
