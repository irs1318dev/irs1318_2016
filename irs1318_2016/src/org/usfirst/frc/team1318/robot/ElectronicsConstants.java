package org.usfirst.frc.team1318.robot;

import edu.wpi.first.wpilibj.DigitalSource;

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

    public static final int PCM_A_MODULE = 1;
    public static final int PCM_B_MODULE = 0;

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_LEFT_TALON_CHANNEL = 2;
    public static final int DRIVETRAIN_RIGHT_TALON_CHANNEL = 1;

    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = 2;
    public static final int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = 3;

    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = 4;
    public static final int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = 5;

    public static final int DRIVETRAIN_PROXIMITY_SENSOR_BACK_PORT = 1;
    public static final int DRIVETRAIN_PROXIMITY_SENSOR_FRONT_PORT = 2;

    //================================================== Autonomous ==============================================================

    public static final int AUTONOMOUS_DIP_SWITCH_A = 8;
    public static final int AUTONOMOUS_DIP_SWITCH_B = 9;

    //================================================= Defense Arm ===============================================================

    public static final int DEFENSE_ARM_MOTOR_CHANNEL = 0;
    public static final int DEFENSE_ARM_FRONT_LIMIT_SWITCH_CHANNEL = 0;
    public static final int DEFENSE_ARM_BACK_LIMIT_SWITCH_CHANNEL = 0;
    public static final int DEFENSE_ARM_ENCODER_CHANNEL_A = 0;
    public static final int DEFENSE_ARM_ENCODER_CHANNEL_B = 0;
    
    //=================================================== Shooter ===================================================================
    
    public static final int SHOOTER_MOTOR_CHANNEL = 0;
    public static final int SHOOTER_ENCODER_CHANNEL_A = 0;
    public static final int SHOOTER_ENCODER_CHANNEL_B = 0;
    
    //=================================================== Intake =================================================================
    public static final int INTAKE_MOTOR_CHANNEL = 0;
    
    // ================================================= Sensor test =============================================================
    
    public static final int TEST_SENSOR_SHARP = 0;
    public static final int TEST_SENSOR_IR_IN = 0;
    public static final int TEST_SENSOR_IR_OUT = 0;
}
