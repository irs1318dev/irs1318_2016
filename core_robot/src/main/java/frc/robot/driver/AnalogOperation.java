package frc.robot.driver;

public enum AnalogOperation implements IOperation
{
    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainLeftVelocity,
    DriveTrainRightVelocity,
    DriveTrainHeadingCorrection,
    PositionStartingAngle,

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

    // Shooter operations:
    ShooterSpeed,

    // Climbing arm operations:
    ClimberWinchSpeed,
}
