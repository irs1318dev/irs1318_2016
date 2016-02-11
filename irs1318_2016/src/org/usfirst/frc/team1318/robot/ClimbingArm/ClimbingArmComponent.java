package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

public class ClimbingArmComponent
{
    private final Talon motor;
    private final DoubleSolenoid sideSolenoid;
    private final DoubleSolenoid armSolenoid;
    private final DigitalInput bottomLimitSwitch;
    private final DigitalInput topLimitSwitch;
    
    public ClimbingArmComponent(){
        this.motor = new Talon(ElectronicsConstants.CLIMBING_ARM_MOTOR_CHANNEL);
        this.sideSolenoid = new DoubleSolenoid(ElectronicsConstants.CLIMBING_ARM_SIDE_SOLENOID_CHANNEL_A, ElectronicsConstants.CLIMBING_ARM_SIDE_SOLENOID_CHANNEL_B);
        this.armSolenoid = new DoubleSolenoid(ElectronicsConstants.CLIMBING_ARM_SIDE_SOLENOID_CHANNEL_A, ElectronicsConstants.CLIMBING_ARM_ARM_SOLENOID_CHANNEL_B);
        this.bottomLimitSwitch = new DigitalInput(ElectronicsConstants.CLIMBING_ARM_BOTTOM_LIMIT_SWITCH_CHANNEL);
        this.topLimitSwitch = new DigitalInput(ElectronicsConstants.CLIMBING_ARM_TOP_LIMIT_SWITCH_CHANNEL);
    }
    
    public void stop(){
        this.motor.set(0.0);
        this.sideSolenoid.set(Value.kOff);
        this.armSolenoid.set(Value.kOff);
    }
    
    // Takes a speed and sets the motor with that speedS
    public void setClimbingSpeed(double motorSpeed){
        this.motor.set(motorSpeed);
    }
    
    // True extends the side solenoid, false retracts it
    public void extendOrRetractSideSolenoid(boolean enable)
    {
        if (enable)
        {
            this.sideSolenoid.set(Value.kForward);
        }
        else 
        {
            this.sideSolenoid.set(Value.kReverse);
        }
    }
    
    // True extends the arm solenoid, false retracts it
    public void extendOrRetractArmSolenoid(boolean enable)
    {
        if (enable)
        {
            this.armSolenoid.set(Value.kForward);
        }
        else
        {
            this.armSolenoid.set(Value.kReverse);
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
