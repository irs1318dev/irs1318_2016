package org.usfirst.frc.team1318.robot.Driver;

public enum Operation
{
    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainShiftGearUp,
    DriveTrainShiftGearDown,
    DriveTrainSimpleMode,
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
    ClimberWinchRetract,
    ClimberHookExtend,
    ClimberHookRetract,
    ClimberArmUp,
    ClimberArmDown,
    
    // Other general operations:
    EnablePID,
    DisablePID,
    CancelBreachMacro,
    
    //Stinger Operations
    StingerOut,
    StingerIn;
}
