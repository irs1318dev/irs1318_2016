package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.Climber.ClimberComponent;
import org.usfirst.frc.team1318.robot.Compressor.CompressorComponent;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.General.PositionManager;
import org.usfirst.frc.team1318.robot.General.PowerManager;
import org.usfirst.frc.team1318.robot.Intake.IntakeComponent;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;
import org.usfirst.frc.team1318.robot.Stinger.StingerComponent;

public class ComponentManager
{
    private CompressorComponent compressorComponent;
    private DriveTrainComponent driveTrainComponent;
    private ShooterComponent shooterComponent;
    private IntakeComponent intakeComponent;
    private ClimberComponent climberComponent;
    private StingerComponent stingerComponent;

    private PowerManager powerManager;
    private PositionManager positionManager;

    public ComponentManager()
    {
        this.compressorComponent = new CompressorComponent();
        this.driveTrainComponent = new DriveTrainComponent();
        this.shooterComponent = new ShooterComponent();
        this.intakeComponent = new IntakeComponent();
        this.stingerComponent = new StingerComponent();
        this.climberComponent = new ClimberComponent();

        this.powerManager = new PowerManager();
        this.positionManager = new PositionManager(this.driveTrainComponent);
    }

    public CompressorComponent getCompressor()
    {
        return this.compressorComponent;
    }

    public DriveTrainComponent getDriveTrain()
    {
        return this.driveTrainComponent;
    }

    public ShooterComponent getShooter() 
    {
        return this.shooterComponent;
    }

    public IntakeComponent getIntake()
    {
        return this.intakeComponent;
    }

    public ClimberComponent getClimberComponent()
    {
        return this.climberComponent;
    }

    public PowerManager getPowerManager()
    {
        return this.powerManager;
    }

    public PositionManager getPositionManager()
    {
        return this.positionManager;
    }
    
    public StingerComponent getStingerComponent()
    {
        return this.stingerComponent;
    }
}
