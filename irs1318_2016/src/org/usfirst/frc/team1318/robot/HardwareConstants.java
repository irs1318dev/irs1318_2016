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

    // TODO update this distance (I feel like it has changed).
    public static final double DRIVETRAIN_WHEEL_SEPARATION_DISTANCE = 24.0 * 2.54;// (in centimeters)

    //================================================== Defense Arm =============================================================
    
    public static final double DEFENSE_ARM_PULSE_DISTANCE = 0;
    public static final double DEFENSE_ARM_FRONT_POSITION = 0;
    public static final double DEFENSE_ARM_PORTCULLIS_POSITION = 0;
    public static final double DEFENSE_ARM_DRAWBRIDGE_POSITION = 0;
    public static final double DEFENSE_ARM_SALLY_PORT_POSITION = 0;
    public static final double DEFENSE_ARM_BACK_POSITION = 0;
    
    public static final double DEFENSE_ARM_LENGTH = 0.0;
    public static final double DEFENSE_ARM_GEARING_RATIO = 20.0/15.0;
    
    public static final double DEFENSE_ARM_PULSES_PER_REVOLUTION = 2000;
    public static final double DEFENSE_ARM_TICKS_PER_RADIAN =  DEFENSE_ARM_PULSES_PER_REVOLUTION * DEFENSE_ARM_GEARING_RATIO / (2.0 * Math.PI);
    //(Radian angle of DEFENSE ARM) * (CONSTANT) should equal the amount of ticks passed on encoder
}
