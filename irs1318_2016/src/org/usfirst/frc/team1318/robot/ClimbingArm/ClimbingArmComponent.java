package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

public class ClimbingArmComponent
{
    private final Talon motor;
    private final DoubleSolenoid shoulderSolenoid;
    private final DoubleSolenoid elbowSolenoid;
    private final DigitalInput bottomLimitSwitch;
    private final DigitalInput topLimitSwitch;
    
    public ClimbingArmComponent()
    {
        this.motor = new Talon(ElectronicsConstants.CLIMBING_ARM_MOTOR_CHANNEL);
        this.shoulderSolenoid = new DoubleSolenoid(ElectronicsConstants.CLIMBING_ARM_SHOULDER_SOLENOID_CHANNEL_A, ElectronicsConstants.CLIMBING_ARM_SHOULDER_SOLENOID_CHANNEL_B);
        this.elbowSolenoid = new DoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.CLIMBING_ARM_ELBOW_SOLENOID_CHANNEL_A, ElectronicsConstants.CLIMBING_ARM_ELBOW_SOLENOID_CHANNEL_B);
        this.bottomLimitSwitch = new DigitalInput(ElectronicsConstants.CLIMBING_ARM_BOTTOM_LIMIT_SWITCH_CHANNEL);
        this.topLimitSwitch = new DigitalInput(ElectronicsConstants.CLIMBING_ARM_TOP_LIMIT_SWITCH_CHANNEL);
    }
    
    public void stop()
    {
        this.motor.set(0.0);
        this.shoulderSolenoid.set(Value.kOff);
        this.elbowSolenoid.set(Value.kOff);
    }
    
    // Takes a speed and sets the motor with that speed
    public void setClimbingSpeed(double motorSpeed)
    {
        this.motor.set(motorSpeed);
    }
    
    // True extends the side solenoid, false retracts it
    public void extendShoulderSolenoid(boolean enable)
    {
        if (enable)
        {
            this.shoulderSolenoid.set(Value.kForward);
        }
        else 
        {
            this.shoulderSolenoid.set(Value.kReverse);
        }
    }
    
    // True extends the arm solenoid, false retracts it
    public void extendElbowSolenoid(boolean enable)
    {
        if (enable)
        {
            this.elbowSolenoid.set(Value.kForward);
        }
        else
        {
            this.elbowSolenoid.set(Value.kReverse);
        }
    }
    
    // Returns the value of the bottom limit switch
    public boolean getBottomLimitSwitch()
    {
        return this.bottomLimitSwitch.get();
    }
    
    // Returns the value of the top limit switch
    public boolean getTopLimitSwitch()
    {
        return this.topLimitSwitch.get();
    }
}
