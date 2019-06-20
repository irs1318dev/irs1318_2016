package frc.robot.driver;

public enum Operation
{
    PositionStartingAngle,

    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainShiftGearUp,
    DriveTrainShiftGearDown,
    DriveTrainSimpleMode,
    DriveTrainUseBrakeMode,
    DriveTrainUsePositionalMode,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainSwapFrontOrientation,
    
    // Defense arm operations:
    DefenseArmMaxFrontPosition,
    DefenseArmHorizontalFrontPosition,
    DefenseArmUpForwardPosition,
    DefenseArmUpPosition,
    DefenseArmMaxBackPosition,
    DefenseArmMolassesMode,
    DefenseArmMoveBack,
    DefenseArmMoveForward,
    DefenseArmTakePositionInput,
    DefenseArmSetAngle,
    DefenseArmUseSensors,
    DefenseArmIgnoreSensors,
    DisableDefenseArmPID, 
    EnableDefenseArmPID,
    DefenseArmSetAtMiddleAngle,
    
    // Shooter operations:
    ShooterSpeed,
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
    ClimberWinchSpeed,
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
