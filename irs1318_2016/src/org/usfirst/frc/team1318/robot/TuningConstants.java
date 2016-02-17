package org.usfirst.frc.team1318.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    //================================================== DriveTrain ==============================================================

    // Drivetrain PID keys/default values:
    public static final boolean DRIVETRAIN_USE_PID_DEFAULT = true;

    // Velocity PID (right)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP_DEFAULT = 0.0;//3;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF_DEFAULT = 0.5;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS_DEFAULT = 100.0;

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP_DEFAULT = 0.0;//3;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KF_DEFAULT = 0.5;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KS_DEFAULT = 100.0;

    // Position PID (right)
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP_DEFAULT = 0.2;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF_DEFAULT = 0.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT = 0.2;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF_DEFAULT = 0.0;

    // Drivetrain max speeds from encoder
    public static final double DRIVETRAIN_LEFT_ENCODER_MAX_SPEED = 1.0;// max speed we expect to detect from the left encoder
    public static final double DRIVETRAIN_RIGHT_ENCODER_MAX_SPEED = 1.0;// max speed we expect to detect from the right encoder

    // Drivetrain choices for one-stick drive
    public static final double DRIVETRAIN_K1 = 1.5;
    public static final double DRIVETRAIN_K2 = 0.4;

    public static final double DRIVETRAIN_A = 0.4;// "a" coefficient (advancing turn)
    public static final double DRIVETRAIN_B = 0.4;// "b" coefficient (in-place turn)

    // Drivetrain deadzone/max power levels
    public static final double DRIVETRAIN_X_DEAD_ZONE = .05;
    public static final double DRIVETRAIN_Y_DEAD_ZONE = .1;
    public static final double DRIVETRAIN_MAX_POWER_LEVEL = 0.775;// max power level (velocity)
    public static final double DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID = 0.2;// max power level (positional, non-PID)

    public static final double DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL = 0.6;
    public static final double DRIVETRAIN_VELOCITY_MAX_POWER_LEVEL = 1.0;

    public static final double DRIVETRAIN_REVERSE_RIGHT_SCALE_FACTOR = 1.15;//moving forwards
    public static final double DRIVETRAIN_REVERSE_LEFT_SCALE_FACTOR = 1.17;//moving backwards

    // Defense arm max velocity (angular)
    public static final double DEFENSE_ARM_MAX_VELOCITY = Math.PI / 4.0;

    // Defense arm power level for when PID is disabled
    public static final double DEFENSE_ARM_OVERRIDE_POWER_LEVEL = 0.5;

    // Defense arm PID values
    public static final double DEFENSE_ARM_POSITION_PID_KP_DEFAULT = 4.0;
    public static final double DEFENSE_ARM_POSITION_PID_KI_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KD_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KF_DEFAULT = 0.0;
    
    public static final double DEFENSE_ARM_MAX_POWER_LEVEL = 0.8;
    public static final double DEFENSE_ARM_MOVE_END_POWER_LEVEL = 0.8;
    public static final double DEFENSE_ARM_PAST_FRONT_POSITION = -1000.0;
    public static final double DEFENSE_ARM_PAST_BACK_POSITION = 1000.0;

    // Distance Robot must travel to breach portcullis
    public static final double PORTCULLIS_BREACH_DISTANCE = 2 * HardwareConstants.DEFENSE_ARM_LENGTH;
    public static final double START_TO_OUTER_WORKS_DISTANCE = 24.0 * 2.54;//74.0 * 2.54;
    // Distance from the outerworks edge to where the robot needs to be to start opening the portcullis
    public static final double PORTCULLIS_OUTER_WORKS_DISTANCE = -9.0 * 2.54;
    
    // Distance Robot must travel to breach portcullis
    public static final double PORTCULLIS_BREACH_VELOCITY = 10.0; // cm/s
    
    // Sally Port Breach Macro
    // Distance robot must drive for first part of sally port breach (first drive backwards)
    public static final double SALLY_PORT_OUTER_WORKS_DISTANCE = 9.0 * 2.54; // TODO: update guess
    public static final double SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS = 9.0 * 2.54; // TODO: update guess
    public static final double SALLY_PORT_BREACH_FORWARD_ARC_RADIUS = 9.0 * 2.54; // TODO: update guess
    public static final double SALLY_PORT_BREACH_FINAL_CHARGE_DISTANCE = 48.0 * 2.54; // TODO: update guess

    // Shooter constants
    public static final double SHOOTER_MAX_POWER_LEVEL = 1.0;

    public static final double SHOOTER_VELOCITY_PID_KP_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KI_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KD_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KF_DEFAULT = 1.0;

    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.85;
    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.675; //0.525;
    public static final double SHOOTER_FIRE_DURATION = 1.5;
    public static final double SHOOTER_SPIN_UP_DURATION = 3.0;
    
    // Intake constants
    public static final double INTAKE_IN_POWER_LEVEL = 0.8;
    public static final double INTAKE_OUT_POWER_LEVEL = -0.8;
    
    // Climbing Arm Constants
    public static final double CLIMBING_ARM_MAX_SPEED = 0.8;
    public static final double CLIMBING_ARM_ELBOW_UP_DURATION = 2.0;
    public static final double CLIMBING_ARM_SHOULDER_UP_DURATION = 2.0;

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 2.0;

    // autonomous defense arm wait until it is in the expected position
    public static final double DEFENSE_ARM_POSITIONAL_ACCEPTABLE_DELTA = Math.PI / 16;

    public static final double DRAWBRIDGE_BACKUP_DISTANCE = 0.0;

    public static final double DRAWBRIDGE_BREACH_VELOCITY = -0.5;

    public static final double DRAWBRIDGE_OUTER_WORKS_DISTANCE = 0.0;

    public static final double MIDLINE_TO_OUTERWORKS_DISTANCE = 0.0;
}
