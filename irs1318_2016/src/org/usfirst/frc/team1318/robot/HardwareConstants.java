package org.usfirst.frc.team1318.robot;

/**
 * All constants describing the physical structure of the robot (distances and sizes of things).
 * 
 * @author Will
 * 
 */
public class HardwareConstants
{
    //================================================== DriveTrain ==============================================================

    public static final double DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION = 360.0;
    public static final double DRIVETRAIN_LEFT_WHEEL_DIAMETER = 6.0 * 2.54;// (in centimeters)
    public static final double DRIVETRAIN_LEFT_PULSE_DISTANCE = Math.PI
        * HardwareConstants.DRIVETRAIN_LEFT_WHEEL_DIAMETER / HardwareConstants.DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION;

    public static final double DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION = 360.0;
    public static final double DRIVETRAIN_RIGHT_WHEEL_DIAMETER = 6.0 * 2.54;// (in centimeters)
    public static final double DRIVETRAIN_RIGHT_PULSE_DISTANCE = Math.PI
        * HardwareConstants.DRIVETRAIN_RIGHT_WHEEL_DIAMETER / HardwareConstants.DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION;

    public static final double DRIVETRAIN_WHEEL_SEPARATION_DISTANCE = 23.75 * 2.54;// (in centimeters)

    public static final double DRIVETRAIN_WHEEL_DISTANCE = 9.0 * 2.54;// in centimeters

    //================================================== Defense Arm =============================================================
    public static final double DEFENSE_ARM_PULSE_DISTANCE = 0;
    public static final double DEFENSE_ARM_BASE_STATE = 0;
    public static final double DEFENSE_ARM_FRONT_STATE = 0;
    public static final double DEFENSE_ARM_BACK_STATE = 0;
    public static final double DEFENSE_ARM_POSITION_1 = 0;
    public static final double DEFENSE_ARM_POSITION_2 = 0;
    public static final double DEFENSE_ARM_POSITION_3 = 0;
    public static final double DEFENSE_ARM_POSITION_4 = 0;
    public static final double DEFENSE_ARM_POSITION_5 = 0;
    public static final double DEFENSE_ARM_LENGTH = 0.0;
}
