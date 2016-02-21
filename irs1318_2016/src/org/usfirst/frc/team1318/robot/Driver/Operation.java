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
    DefenseArmMoveBack,
    DefenseArmMoveForward,
    DefenseArmTakePositionInput,
    DefenseArmSetAngle,
    DefenseArmUseSensors,
    DefenseArmIgnoreSensors,
    DisableDefenseArmPID, 
    EnableDefenseArmPID,
    
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
    ClimbingArmExtend,
    ClimbingArmRetract,
    ClimbingArmShoulderUp,
    ClimbingArmShoulderDown,
    ClimbingArmElbowUp,
    ClimbingArmElbowDown,
    
    // Other general operations:
    EnablePID,
    DisablePID,
    CancelBreachMacro;
}
