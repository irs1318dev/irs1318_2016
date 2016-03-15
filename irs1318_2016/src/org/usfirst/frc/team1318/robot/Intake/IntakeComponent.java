package org.usfirst.frc.team1318.robot.Intake;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.Common.DashboardLogger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

public class IntakeComponent
{
    private final Talon motor;
    private final DoubleSolenoid intakeBaseSolenoid;
    private final DoubleSolenoid intakeExtensionSolenoid;
    private final Solenoid intakeLight;
    private final AnalogInput throughBeamSensor;

    public IntakeComponent() 
    {
        this.motor = new Talon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
        this.intakeBaseSolenoid = new DoubleSolenoid(
            ElectronicsConstants.INTAKE_BASE_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_BASE_SOLENOID_CHANNEL_B);
        this.intakeExtensionSolenoid = new DoubleSolenoid(
            ElectronicsConstants.INTAKE_EXTENSION_SOLENOID_CHANNEL_A, 
            ElectronicsConstants.INTAKE_EXTENSION_SOLENOID_CHANNEL_B);
        this.intakeLight = new Solenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_LIGHT_CHANNEL);
        this.throughBeamSensor = new AnalogInput(ElectronicsConstants.INTAKE_THROUGH_BEAM_SENSOR_CHANNEL);
    }

    /**
     * Extend or retract the "base" part of the intake
     * @param extend - true extends
     */
    public void extendOrRetractBase(boolean extend)
    {
        if (extend)
        {
            this.intakeBaseSolenoid.set(Value.kForward);
        }
        else
        {
            this.intakeBaseSolenoid.set(Value.kReverse);
        }
    }
    
    /**
     * Extend or retract the "feet" extension of the intake
     * @param extend - true extends
     */
    public void extendOrRetractExtension(boolean extend)
    {
        if(extend)
        {
            this.intakeExtensionSolenoid.set(Value.kForward);
        }
        else
        {
            this.intakeExtensionSolenoid.set(Value.kReverse);
        }
    }

    public void stop()
    {
        this.motor.set(0.0);
        this.intakeBaseSolenoid.set(Value.kOff);
        this.intakeLight.set(false);
    }

    /**
     * Take a speed and sets the motor to that speed
     * @param speed - the speed to be set
     */
    public void setIntakeSpeed(double speed)
    {
        this.motor.set(speed);
    }

    public boolean getThroughBeamBroken()
    {
        boolean valueBool = (this.throughBeamSensor.getVoltage() < 2.5);
        DashboardLogger.putBoolean("Through beam broken", valueBool);
        return valueBool;
    }

    public void setIntakeLight(boolean enable)
    {
        this.intakeLight.set(enable);
    }
}
