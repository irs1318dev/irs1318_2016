package frc.robot.driver;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    // DriveTrain operations:
    PIDBrake,

    // Shooter operations:
    SpinFar,
    SpinMiddle,
    SpinClose,
    Shoot,

    // Climbing arm operations:
    ClimberArmDeploy,
    ClimberArmRetract,
    ClimberScale;
}