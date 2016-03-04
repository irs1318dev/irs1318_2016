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
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KP_DEFAULT = 0.2;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KI_DEFAULT = 0.05;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KF_DEFAULT = 0.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT = 0.2;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT = 0.05;
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

    //================================================== DefenseArm ==============================================================

    public static final boolean DEFENSE_ARM_USE_SENSORS_DEFAULT = true;
    public static final double DEFENSE_ARM_STARTING_POSITION_DEFAULT = Math.PI / 2.0;

    // Defense arm max velocity (angular)
    public static final double DEFENSE_ARM_MAX_VELOCITY = Math.PI / 2.0;
    public static final double DEFENSE_ARM_MOLASSES_VELOCITY = Math.PI / 8.0;

    // Defense arm power level for when PID is disabled
    public static final double DEFENSE_ARM_OVERRIDE_POWER_LEVEL = 0.4;

    // Defense arm PID values
    public static final double DEFENSE_ARM_POSITION_PID_KP_DEFAULT = 4.0;
    public static final double DEFENSE_ARM_POSITION_PID_KI_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KD_DEFAULT = 0.0;
    public static final double DEFENSE_ARM_POSITION_PID_KF_DEFAULT = 0.0;

    public static final double DEFENSE_ARM_MAX_POWER_LEVEL = 0.8;
    public static final double DEFENSE_ARM_MOVE_END_POWER_LEVEL = 0.4;

    public static final double DEFENSE_ARM_UP_FORWARD_POSITION = Math.PI / 4.0;
    public static final double DEFENSE_ARM_UP_POSITION = Math.PI / 2.0;
    public static final double DEFENSE_ARM_OVERRIDE_AMOUNT = Math.PI/64.0;

    //================================================== Shooter ==============================================================

    public static final boolean SHOOTER_SCALE_BASED_ON_VOLTAGE = true;
    public static final double SHOOTER_VELOCITY_TUNING_VOLTAGE = 12.5;

    public static final double SHOOTER_MAX_POWER_LEVEL = 1.0;

    public static final double SHOOTER_VELOCITY_PID_KP_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KI_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KD_DEFAULT = 0.0;
    public static final double SHOOTER_VELOCITY_PID_KF_DEFAULT = 1.0;
    public static final double SHOOTER_VELOCITY_PID_KS_DEFAULT = 100.0;

    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.80;
    public static final double SHOOTER_MIDDLE_SHOT_VELOCITY = 0.52;
    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.85;
    public static final double SHOOTER_LOWER_KICKER_DURATION = 0.5;
    public static final double SHOOTER_SPIN_UP_DURATION = 1.75;
    public static final double SHOOTER_FIRE_DURATION = 1.0;

    //================================================== Intake ==============================================================

    public static final double INTAKE_IN_POWER_LEVEL = 0.8;
    public static final double INTAKE_OUT_POWER_LEVEL = -0.8;

    //================================================== ClimbingArm ==============================================================

    public static final double CLIMBING_ARM_MAX_SPEED = 0.8;
    public static final double CLIMBING_ARM_ELBOW_UP_DURATION = 2.0;
    public static final double CLIMBING_ARM_SHOULDER_UP_DURATION = 2.0;

    //================================================== Breach Macros ==============================================================

    public static final double START_TO_OUTER_WORKS_DISTANCE = 24.0 * 2.54;//74.0 * 2.54;

    //----- Portcullis -----

    public static final double DEFENSE_ARM_PORTCULLIS_BREACH_APPROACH_POSITION = -Math.PI/32.0;
    public static final double DEFENSE_ARM_PORTCULLIS_BREACH_CAPTURE_POSITION = Math.PI * 1.0/8.0;
    public static final double PORTCULLIS_BREACH_DISTANCE = 2 * HardwareConstants.DEFENSE_ARM_LENGTH;
    public static final double PORTCULLIS_OUTER_WORKS_DISTANCE = 9.0 * 2.54;
    public static final double PORTCULLIS_BREACH_VELOCITY = 20.0; // cm/s

    //----- SallyPort -----

    public static final double SALLY_PORT_OUTER_WORKS_DRIVE_DISTANCE = -9.0 * 2.54;
    public static final double SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS = 9.0 * 2.54;
    public static final double SALLY_PORT_BREACH_FORWARD_ARC_RADIUS = 9.0 * 2.54;
    public static final double SALLY_PORT_BREACH_FINAL_CHARGE_DISTANCE = 48.0 * 2.54;
    public static final double DEFENSE_ARM_SALLY_PORT_APPROACH_POSITION = 2.224;
    public static final double DEFENSE_ARM_SALLY_PORT_PULL_POSITION = 2.37;

    //----- Drawbridge -----

    public static final double DRAWBRIDGE_BACKUP_DISTANCE = 0.0;
    public static final double DRAWBRIDGE_BREACH_VELOCITY = -0.5;
    public static final double DRAWBRIDGE_OUTER_WORKS_DISTANCE = 0.0;
    public static final double DEFENSE_ARM_DRAWBRIDGE_APPROACH_POSITION = Math.PI * 1.0/2.0;
    public static final double DEFENSE_ARM_DRAWBRIDGE_CAPTURE_POSITION = Math.PI * 3.0/8.0;

    //----- Cheval de Frise -----

    public static final double CHEVAL_DE_FRISE_OUTER_WORKS_DISTANCE = 0.0;
    public static final double CHEVAL_DE_FRISE_HALF_BREACH_DISTANCE = 0.0;
    public static final double CHEVAL_DE_FRISE_REMAINING_BREACH_DISTANCE = 0.0;
    public static final double DEFENSE_ARM_CHEVAL_DE_FRISE_APPROACH_POSITION = Math.PI/4;
    public static final double DEFENSE_ARM_CHEVAL_DE_FRISE_CAPTURE_POSITION = HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION;
    
    //----- Lowbar -----
    public static final double DEFENSE_ARM_LOWBAR_APPROACH_POSITION = 0.0;

    //================================================== Autonomous ==============================================================

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 0.5;
    public static final double DEFENSE_ARM_POSITIONAL_ACCEPTABLE_DELTA = Math.PI / 64;
    
    // Drivetrain autonomous velocity values
    public static final double DRIVETRAIN_AUTONOMOUS_SLOW_VELOCITY = .3;
    public static final double DRIVETRAIN_AUTONOMOUS_FAST_VELOCITY = .45;
    
    public static final double AUTONOMOUS_DEFENSE_BREACH_DISTANCE = 20 * 12 * 2.54;// Distance from start through a defense in autonomous
    public static final double AUTONOMOUS_TIME = 15.0;
}
