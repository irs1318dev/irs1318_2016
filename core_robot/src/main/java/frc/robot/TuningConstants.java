package frc.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.mechanisms.*;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = true;
    public static boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    public static List<IMechanism> GetActiveMechanisms(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<IMechanism>();
        mechanismList.add(injector.getInstance(DriveTrainMechanism.class));
        mechanismList.add(injector.getInstance(PowerManager.class));
        mechanismList.add(injector.getInstance(PositionManager.class));
        mechanismList.add(injector.getInstance(CompressorMechanism.class));
        mechanismList.add(injector.getInstance(IntakeMechanism.class));
        mechanismList.add(injector.getInstance(ShooterMechanism.class));
        mechanismList.add(injector.getInstance(StingerMechanism.class));
        //mechanismList.add(injector.getInstance(ClimberMechanism.class));
        return mechanismList;
    }

    //================================================== Autonomous ==============================================================

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;

    public static final double DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA = 1.0;
    
    // Drivetrain autonomous velocity values
    public static final double DRIVETRAIN_AUTONOMOUS_SLOW_VELOCITY = .3;
    public static final double DRIVETRAIN_AUTONOMOUS_FAST_VELOCITY = .45;
    
    public static final double AUTONOMOUS_DEFENSE_BREACH_DISTANCE = 20 * 12 * 2.54;// Distance from start through a defense in autonomous
    public static final double AUTONOMOUS_TIME_SLOW = 4.0;
    public static final double AUTONOMOUS_TIME_FAST = 3.0;
    
    // Cheval De Frise values
    public static final double AUTONOMOUS_CHEVAL_BREACH_TIME = 3;
    public static final double AUTONOMOUS_CHEVAL_BREACH_DISTANCE = 500;
    
    // Portcullis values
    public static final double AUTONOMOUS_PORTCULLIS_BREACH_TIME = 3;
    public static final double AUTONOMOUS_PORTCULLIS_BREACH_DISTANCE = 500;

    public static final double START_TO_PORTCULLIS_DISTANCE = 24.0 * 2.54;//74.0 * 2.54;
    public static final double START_TO_CHEVAL_DE_FRISE_DISTANCE = 24.0 * 2.54;//74.0 * 2.54;

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
    public static final double DRIVETRAIN_POSITION_PID_RIGHT_KS_DEFAULT = 1.0;

    // Position PID (left)
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KP_DEFAULT = 0.15;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KI_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KD_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KF_DEFAULT = 0.0;
    public static final double DRIVETRAIN_POSITION_PID_LEFT_KS_DEFAULT = 1.0;

    // Brake PID (right)
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KP = 0.15;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KI = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KD = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_RIGHT_KF = 0.0;

    // Brake PID (left)
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KP = 0.15;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KI = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KD = 0.0;
    public static final double DRIVETRAIN_BRAKE_PID_LEFT_KF = 0.0;

    // Drivetrain choices for one-stick drive
    public static final double DRIVETRAIN_K1 = 1.4;
    public static final double DRIVETRAIN_K2 = 0.5;

    // Drivetrain deadzone/max power levels
    public static final double DRIVETRAIN_X_DEAD_ZONE = .05;
    public static final double DRIVETRAIN_Y_DEAD_ZONE = .1;
    public static final double DRIVETRAIN_MAX_POWER_LEVEL = 0.775;// max power level (velocity)
    public static final double DRIVETRAIN_LEFT_POSITIONAL_NON_PID_MULTIPLICAND = HardwareConstants.DRIVETRAIN_LEFT_PULSE_DISTANCE / 60.0;
    public static final double DRIVETRAIN_RIGHT_POSITIONAL_NON_PID_MULTIPLICAND = HardwareConstants.DRIVETRAIN_RIGHT_PULSE_DISTANCE / 60.0;
    public static final double DRIVETRAIN_MAX_POWER_POSITIONAL_NON_PID = 0.2;// max power level (positional, non-PID)

    public static final double DRIVETRAIN_ENCODER_ODOMETRY_ANGLE_CORRECTION = 1.0;

    public static final double DRIVETRAIN_POSITIONAL_MAX_POWER_LEVEL = 0.90; // 0.85
    public static final double DRIVETRAIN_BRAKE_MAX_POWER_LEVEL = 0.6;
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

    public static final double SHOOTER_CLOSE_SHOT_VELOCITY = 0.6; //0.7;
    public static final double SHOOTER_MIDDLE_SHOT_VELOCITY = 0.52; //0.52;
    public static final double SHOOTER_FAR_SHOT_VELOCITY = 0.8; //0.7;
   
    public static final double SHOOTER_LOWER_KICKER_DURATION = 0.5;
    public static final double SHOOTER_SPIN_UP_DURATION = 1.75;
    public static final double SHOOTER_FIRE_DURATION = 0.75;

    public static final double SHOOTER_REVERSE_DURATION = 0.5;
    
    public static final double SHOOTER_DEVIANCE = 0.025;
    public static final double SHOOTER_MAX_COUNTER_RATE = 2000;
    
    public static final double SHOOTER_TARGETING_LIGHT_ACTIVATION_THRESHOLD = 0.5;

    //================================================== Intake ==============================================================

    public static final double INTAKE_IN_POWER_LEVEL = 0.9;
    public static final double INTAKE_OUT_POWER_LEVEL = -0.9;

    //================================================== Climber ==============================================================

    public static final double CLIMBER_WINCH_MAX_SPEED = 0.8;
    public static final double CLIMBER_HOOK_MAX_SPEED = 0.8;
    public static final double CLIMBER_ENCODER_DISTANCE_PER_PULSE = 1.0;
    public static final double CLIMBER_ACCEPTABLE_DELTA = 0.025;
    public static final double CLIMBER_FIRE_DURATION = 0.5;
    public static final double CLIMBER_CLIMB_DURATION = 9.0;
    public static final double CLIMBER_CLIMB_DISTANCE = 1000.0;

    //================================================== Stinger ==============================================================

    public static final double STINGER_MAX_VELOCTIY = 0.5;
    public static final double STINGER_SLOW_BACK_VELOCTIY = -0.275;
}
