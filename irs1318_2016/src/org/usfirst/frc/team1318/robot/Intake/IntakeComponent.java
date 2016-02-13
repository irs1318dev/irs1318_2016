package org.usfirst.frc.team1318.robot.Intake;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

public class IntakeComponent
{
    private final Talon motor;
    private final DoubleSolenoid solenoid;

    public IntakeComponent() 
    {
        this.motor = new Talon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
        this.solenoid = new DoubleSolenoid(
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_B);
    }
    
    // True extends the intake, false retracts it.
    public void extendOrRetract(boolean extend)
    {
        if (extend)
        {
            this.solenoid.set(Value.kForward);
        }
        else
        {
            this.solenoid.set(Value.kReverse);
        }
    }
    
    public void stop()
    {
        this.motor.set(0.0);
        this.solenoid.set(Value.kOff);
    }
    
    // takes a speed and sets the motor with that speed
    public void setIntakeSpeed(double speed)
    {
        this.motor.set(speed);
    }
}
