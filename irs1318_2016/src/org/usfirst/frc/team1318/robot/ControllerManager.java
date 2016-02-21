package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Compressor.CompressorController;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmController;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Intake.IntakeController;
import org.usfirst.frc.team1318.robot.SensorManager.SensorManagerController;
import org.usfirst.frc.team1318.robot.Shooter.ShooterController;

public class ControllerManager implements IController
{
    public final ComponentManager components;
    public final ArrayList<IController> controllerList;
    
    public ControllerManager(ComponentManager components)
    {
        this.components = components;
        this.controllerList = new ArrayList<IController>();
        //this.controllerList.add(new CompressorController(this.components.getCompressor()));
        //this.controllerList.add(new DriveTrainController(this.components.getDriveTrain(), TuningConstants.DRIVETRAIN_USE_PID_DEFAULT));
        //this.controllerList.add(new DefenseArmController(this.components.getDefenseArm()));
        //this.controllerList.add(new IntakeController(this.components.getIntake()));
        //this.controllerList.add(new ShooterController(this.components.getShooter()));
        //this.controllerList.add(new ClimbingArmController(this.components.getClimbingArm()));
        this.controllerList.add(new SensorManagerController(this.components.getSensorManager()));
    }

    @Override
    public void update()
    {
        for (IController controller : this.controllerList)
        {
            controller.update();
        }
    }

    @Override
    public void stop()
    {
        for (IController controller : this.controllerList)
        {
            controller.stop();
        }
    }

    @Override
    public void setDriver(Driver driver)
    {
        for (IController controller : this.controllerList)
        {
            controller.setDriver(driver);
        }
    }
}
