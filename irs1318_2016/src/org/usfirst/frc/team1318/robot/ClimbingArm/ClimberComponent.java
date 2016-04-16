package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

/**
 * The class for the climber component. 
 * 
 * @author Corbin
 *
 */

public class ClimberComponent
{
    private final Talon winch;
    private final Talon hook;
    private final DoubleSolenoid arm;
    
    public ClimberComponent()
    {
        this.winch = new Talon(ElectronicsConstants.CLIMBER_WINCH_MOTOR_CHANNEL);
        this.hook = new Talon(ElectronicsConstants.CLIMBER_HOOK_MOTOR_CHANNEL);
        this.arm = new DoubleSolenoid(
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_A, 
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_B);
    }
    
    public void stop()
    {
        this.winch.set(0.0);
        this.hook.set(0.0);
        this.arm.set(Value.kOff);
    }
    
    // Takes a speed and sets the winch with that speed
    public void setWinchSpeed(double motorSpeed)
    {
        this.winch.set(motorSpeed);
    }
    
    // Takes a speed and sets the hook with that speed
    public void setHookSpeed(double motorSpeed)
    {
        this.hook.set(motorSpeed);
    }
    
    // True extends the side solenoid, false retracts it
    public void extendArm(boolean enable)
    {
        if (enable)
        {
            this.arm.set(Value.kForward);
        }
        else 
        {
            this.arm.set(Value.kReverse);
        }
    }
}
