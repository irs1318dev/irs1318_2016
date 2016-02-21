package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.ClimbingArm.ClimbingArmComponent;
import org.usfirst.frc.team1318.robot.Compressor.CompressorComponent;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmComponent;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Intake.IntakeComponent;
import org.usfirst.frc.team1318.robot.SensorManager.SensorManagerComponent;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;

public class ComponentManager
{
    private CompressorComponent compressorComponent;
    private DriveTrainComponent driveTrainComponent;
    private DefenseArmComponent defenseArmComponent;
    private ShooterComponent shooterComponent;
    private IntakeComponent intakeComponent;
    private ClimbingArmComponent climbingArmComponent;
    private SensorManagerComponent sensorManagerComponent;

    public ComponentManager()
    {
        //this.compressorComponent = new CompressorComponent();
        //this.driveTrainComponent = new DriveTrainComponent();
        //this.defenseArmComponent = new DefenseArmComponent();
        //this.shooterComponent = new ShooterComponent();
        //this.intakeComponent = new IntakeComponent();
        //this.climbingArmComponent = new ClimbingArmComponent();
        this.sensorManagerComponent = new SensorManagerComponent();
    }

    public CompressorComponent getCompressor()
    {
        return this.compressorComponent;
    }

    public DriveTrainComponent getDriveTrain()
    {
        return this.driveTrainComponent;
    }

    public DefenseArmComponent getDefenseArm()
    {
        return this.defenseArmComponent;
    }
    
    public ShooterComponent getShooter() 
    {
        return this.shooterComponent;
    }
    
    public IntakeComponent getIntake()
    {
        return this.intakeComponent;
    }
    
    public ClimbingArmComponent getClimbingArm()
    {
        return this.climbingArmComponent;
    }
    
    public SensorManagerComponent getSensorManager()
    {
        return this.sensorManagerComponent;
    }
}
