package frc.robot.driver;

public enum DigitalOperation implements IOperation
{
    // Vision operations:
    VisionForceDisable,
    VisionDisable,
    VisionEnable,
    VisionEnableOffboardStream,
    VisionEnableOffboardProcessing,

    // Compressor operations:
    CompressorForceDisable,

    // DriveTrain operations:
    DriveTrainEnablePID,
    DriveTrainDisablePID,
    DriveTrainSimpleMode,
    DriveTrainUseBrakeMode,
    DriveTrainUsePositionalMode,
    DriveTrainUseSimplePathMode,
    DriveTrainUsePathMode,
    DriveTrainSwapFrontOrientation,

    // Defense arm operations:
    DefenseArmMolassesMode,
    DefenseArmUseSensors,
    DefenseArmIgnoreSensors,
    DisableDefenseArmPID, 
    EnableDefenseArmPID,
    DefenseArmSetAtMiddleAngle,

    // Shooter operations:
    ShooterSpin,
    ShooterLowerKicker,
    ShooterExtendHood,
    
    // Intake operations:
    IntakeRotatingIn,
    IntakeRotatingOut,
    IntakeExtend,
    IntakeRetract,
    
    // Climbing arm operations:
    ClimberWinchExtend,
    ClimberFiringPinExtend,
    ClimberFiringPinRetract,
    ClimberArmUp,
    ClimberArmDown,
    
    // Other general operations:
    EnablePID,
    DisablePID,
    CancelBreachMacro,
    
    // Stinger Operations
    StingerOut,
    StingerIn;
}
