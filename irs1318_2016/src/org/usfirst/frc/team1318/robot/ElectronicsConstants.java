package org.usfirst.frc.team1318.robot;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_Y_AXIS = true;

    public static final double MAX_POWER_LEVEL = 1.0;

    public static final int PCM_A_MODULE = 0;
    public static final int PCM_B_MODULE = 1;

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_LEFT_TALON_CHANNEL = 5;
    public static final int DRIVETRAIN_RIGHT_TALON_CHANNEL = 4;

    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = 0;
    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = 1;

    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = 2;
    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = 3;

    //================================================== Autonomous ==============================================================

    public static final int AUTONOMOUS_DIP_SWITCH_A = 10;
    public static final int AUTONOMOUS_DIP_SWITCH_B = 11;
    public static final int AUTONOMOUS_DIP_SWITCH_C = 12;

    //=================================================== Shooter ===================================================================

    public static final int SHOOTER_TALON_CHANNEL = 1;
    public static final int SHOOTER_KICKER_CHANNEL_A = 2;
    public static final int SHOOTER_KICKER_CHANNEL_B = 3;
    public static final int SHOOTER_HOOD_CHANNEL_A = 5;
    public static final int SHOOTER_HOOD_CHANNEL_B = 2;
    public static final int SHOOTER_ENCODER_CHANNEL_A = 8;
    public static final int SHOOTER_ENCODER_CHANNEL_B = 9;
    public static final int SHOOTER_LIGHT_PORT = 1;

    //=================================================== Intake =================================================================

    public static final int INTAKE_MOTOR_CHANNEL = 3;
    public static final int INTAKE_LIGHT_CHANNEL = 0;
    public static final int INTAKE_THROUGH_BEAM_SENSOR_CHANNEL = 0;

    public static final int INTAKE_SOLENOID_CHANNEL_A = 4;
    public static final int INTAKE_SOLENOID_CHANNEL_B = 3;

    // ================================================= Climbing Arm =============================================================

    public static final int CLIMBING_ARM_MOTOR_CHANNEL = 2;

    public static final int CLIMBING_ARM_SHOULDER_SOLENOID_CHANNEL_A = 7;
    public static final int CLIMBING_ARM_SHOULDER_SOLENOID_CHANNEL_B = 0;

    public static final int CLIMBING_ARM_ELBOW_SOLENOID_CHANNEL_A = 0;
    public static final int CLIMBING_ARM_ELBOW_SOLENOID_CHANNEL_B = 1;

    public static final int CLIMBING_ARM_BOTTOM_LIMIT_SWITCH_CHANNEL = 0;
    public static final int CLIMBING_ARM_TOP_LIMIT_SWITCH_CHANNEL = 0;

    public static final int STINGER_MOTOR_CHANNEL = 0;
}
