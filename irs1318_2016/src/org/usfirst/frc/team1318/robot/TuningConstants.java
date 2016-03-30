package org.usfirst.frc.team1318.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean THROW_EXCEPTIONS = false;
    
    //================================================== DriveTrain ==============================================================

    // Drivetrain PID keys/default values:
    public static final boolean DRIVETRAIN_USE_PID_DEFAULT = true;

    // Velocity PID (right)
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KP_DEFAULT = 0.0275;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KF_DEFAULT = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_RIGHT_KS_DEFAULT = 100.0;

    // Velocity PID (left)
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KP_DEFAULT = 0.0275;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KF_DEFAULT = 0.4;
    public static final double DRIVETRAIN_VELOCITY_PID_LEFT_KS_DEFAULT = 100.0;

    // Position PID (right)
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP_DEFAULT = 0.15;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF_DEFAULT = 0.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT = 0.15;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF_DEFAULT = 0.0;

    // Drivetrain choices for one-stick drive
    public static final double DRIVETRAIN_K1 = 1.4;
    public static final double DRIVETRAIN_K2 = 0.5;

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

    //================================================== Shooter ==============================================================

    public static final boolean SHOOTER_SCALE_BASED_ON_VOLTAGE = false;
    public static final double SHOOTER_VELOCITY_TUNING_VOLTAGE = 12.5;

    public static final double SHOOTER_MAX_POWER_LEVEL = 1.0;

    public static final double SHOOTER_VELOCITY_PID_KP_DEFAULT = 0.1;
    public static final double SHOOTER_VELOCITY_PID_KI_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KD_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KF_DEFAULT = 1.0;
    public static final double SHOOTER_VELOCITY_PID_KS_DEFAULT = 40.0;

    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.7;
    public static final double SHOOTER_MIDDLE_SHOT_VELOCITY = 0.52;
    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.7;
   
    public static final double SHOOTER_LOWER_KICKER_DURATION = 0.5;
    public static final double SHOOTER_SPIN_UP_DURATION = 1.75;
    public static final double SHOOTER_FIRE_DURATION = 0.75;

    public static final double SHOOTER_REVERSE_DURATION = 0.5;
    
    public static final double SHOOTER_DEVIANCE = 0.025;
    public static final double SHOOTER_MAX_COUNTER_RATE = 2000;

    //================================================== Intake ==============================================================

    public static final double INTAKE_IN_POWER_LEVEL = 0.8;
    public static final double INTAKE_OUT_POWER_LEVEL = -0.8;

    //================================================== ClimbingArm ==============================================================

    public static final double CLIMBING_ARM_MAX_SPEED = 0.8;
    public static final double CLIMBING_ARM_ELBOW_UP_DURATION = 2.0;
    public static final double CLIMBING_ARM_SHOULDER_UP_DURATION = 2.0;

    //================================================== Stinger ==============================================================

    public static final double STINGER_MAX_VELOCTIY = 0.5;
    public static final double STINGER_SLOW_BACK_VELOCTIY = 0.275;

    //================================================== Breach Macros ==============================================================

    public static final double START_TO_OUTER_WORKS_DISTANCE = 24.0 * 2.54;//74.0 * 2.54;

    //================================================== Autonomous ==============================================================

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 0.5;
    
    // Drivetrain autonomous velocity values
    public static final double DRIVETRAIN_AUTONOMOUS_SLOW_VELOCITY = .3;
    public static final double DRIVETRAIN_AUTONOMOUS_FAST_VELOCITY = .45;
    
    public static final double AUTONOMOUS_DEFENSE_BREACH_DISTANCE = 20 * 12 * 2.54;// Distance from start through a defense in autonomous
    public static final double AUTONOMOUS_TIME_SLOW = 4.0;
    public static final double AUTONOMOUS_TIME_FAST = 3.0;
}
