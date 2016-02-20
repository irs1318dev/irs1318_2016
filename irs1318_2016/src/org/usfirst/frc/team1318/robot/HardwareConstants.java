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
    public static final double DRIVETRAIN_LEFT_WHEEL_DIAMETER = 7.65 * 2.54;// (in centimeters)
    public static final double DRIVETRAIN_LEFT_PULSE_DISTANCE = Math.PI
        * HardwareConstants.DRIVETRAIN_LEFT_WHEEL_DIAMETER / HardwareConstants.DRIVETRAIN_LEFT_ENCODER_PULSES_PER_REVOLUTION;

    public static final double DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION = 360.0;
    public static final double DRIVETRAIN_RIGHT_WHEEL_DIAMETER = 7.65 * 2.54;// (in centimeters)
    public static final double DRIVETRAIN_RIGHT_PULSE_DISTANCE = Math.PI
        * HardwareConstants.DRIVETRAIN_RIGHT_WHEEL_DIAMETER / HardwareConstants.DRIVETRAIN_RIGHT_ENCODER_PULSES_PER_REVOLUTION;

    public static final double DRIVETRAIN_WHEEL_SEPARATION_DISTANCE = 24.0 * 2.54;// (in centimeters)

    //================================================== Defense Arm =============================================================

    public static final double DEFENSE_ARM_FULL_SWING = HardwareConstants.DEFENSE_ARM_MAX_BACK_POSITION - HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION;
    public static final double DEFENSE_ARM_HORIZONTAL_FRONT_POSITION = 0;
    public static final double DEFENSE_ARM_MAX_FRONT_POSITION = -Math.PI/32.0;
    public static final double DEFENSE_ARM_PORTCULLIS_POSITION = -Math.PI/32.0;
    public static final double CHEVAL_DE_FRISE_DEFENSE_ARM_POSITION_B = Math.PI/16;
    public static final double CHEVAL_DE_FRISE_DEFENSE_ARM_POSITION_A = Math.PI/4;
    public static final double DEFENSE_ARM_SALLY_PORT_POSITION = Math.PI * 3.0/4.0;
    public static final double DEFENSE_ARM_DRAWBRIDGE_POSITION = Math.PI/2.0;
    public static final double DEFENSE_ARM_HORIZONTAL_BACK_POSITION = Math.PI;
    public static final double DEFENSE_ARM_MAX_BACK_POSITION = Math.PI * 33.0/32.0;

    public static final double DEFENSE_ARM_LENGTH = 24.5 * 2.54;
    public static final double DEFENSE_ARM_GEARING_RATIO = 28.0/15.0;

    public static final double DEFENSE_ARM_PULSES_PER_REVOLUTION = 500;
    public static final double DEFENSE_ARM_TICKS_PER_RADIAN = DEFENSE_ARM_PULSES_PER_REVOLUTION * DEFENSE_ARM_GEARING_RATIO / (2.0 * Math.PI);
    public static final double DEFENSE_ARM_PULSE_DISTANCE = 1.0 / DEFENSE_ARM_TICKS_PER_RADIAN;

    public static final double DEFENSE_ARM_DRAWBRIDGE_EXTENSION_LENGTH = 0.0;
}
