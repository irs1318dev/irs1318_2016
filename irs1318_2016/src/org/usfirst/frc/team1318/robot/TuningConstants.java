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
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP_DEFAULT = 0.03;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF_DEFAULT = 0.5;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS_DEFAULT = 100.0;

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP_DEFAULT = 0.03;
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

    // Defense arm max speed
    public static final double DEFENSE_ARM_MAX_VELOCITY = 0.0;

    // Defense arm max power level
    public static final double DEFENSE_ARM_OVERRIDE_POWER_LEVEL = 0.0;

    // Defense arm PID values
    public static final double DEFENSE_ARM_POSITION_PID_KP_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KI_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KD_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KF_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_MAX_POWER_LEVEL = 0.0;
    public static final double DEFENSE_ARM_BELOW_FRONT_POSITION = 0.0;
    public static final double DEFENSE_ARM_PAST_FRONT_POSITION = 0.0;
    public static final double DEFENSE_ARM_PAST_BACK_POSITION = 0.0;

    // MACROS
    public static final double PORTCULLIS_BREACH_DISTANCE = 0.0;
        //Distance Robot must travel to breach portcullis
    public static final double PORTCULLIS_BREACH_ITERATIVE = 0.0;
    public static final double DEFENSE_ARM_RADIANS_TO_TICKS = 0.0;
        //(Radian angle of DEFENSE ARM) * (CONSTANT) should equal the amount of ticks passed on encoder
    
    // Shooter constants
    public static final double SHOOTER_K1 = 1.0;
    public static final double SHOOTER_MAX_POWER_LEVEL = 1.0;

    public static final double SHOOTER_VELOCITY_PID_KP_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KI_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KD_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KF_DEFAULT = 0.0;

    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.0;
    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.0;
    public static final double SHOOTER_FIRE_TIME = 1.0;
    public static final double SHOOTER_SPIN_UP_DURATION = 1.0;
}
