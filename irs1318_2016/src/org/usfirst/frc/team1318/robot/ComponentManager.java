package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.ClimbingArm.ClimbingArmComponent;
import org.usfirst.frc.team1318.robot.Compressor.CompressorComponent;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmComponent;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Intake.IntakeComponent;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;
import org.usfirst.frc.team1318.robot.TestMechanism.TestComponent;

public class ComponentManager
{
    private CompressorComponent compressorComponent;
    private DriveTrainComponent driveTrainComponent;
    private TestComponent testComponent;
    private DefenseArmComponent defenseArmComponent;
    private ShooterComponent shooterComponent;
    private IntakeComponent intakeComponent;
    private ClimbingArmComponent climbingArmComponent;

    public ComponentManager()
    {
        this.compressorComponent = new CompressorComponent();
        this.testComponent = new TestComponent();
        //this.driveTrainComponent = new DriveTrainComponent();
        //this.defenseArmComponent = new DefenseArmComponent();
        //this.shooterComponent = new ShooterComponent();
        //this.intakeComponent = new IntakeComponent();
        //this.climbingArmComponent = new ClimbingArmComponent();
    }

    public CompressorComponent getCompressor()
    {
        return this.compressorComponent;
    }

    public DriveTrainComponent getDriveTrain()
    {
        return this.driveTrainComponent;
    }

    public TestComponent getTest()
    {
        return this.testComponent;
    }

    public DefenseArmComponent getDefenseArm()
    {
        return this.defenseArmComponent;
    }
    
    public ShooterComponent getShooterComponent() 
    {
        return this.shooterComponent;
    }
    
    public IntakeComponent getIntakeComponent()
    {
        return this.intakeComponent;
    }
    
    public ClimbingArmComponent getClimbingArmComponent()
    {
        return this.climbingArmComponent;
    }
}
