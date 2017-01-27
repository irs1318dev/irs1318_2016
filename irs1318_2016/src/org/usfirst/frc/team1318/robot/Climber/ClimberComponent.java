package org.usfirst.frc.team1318.robot.Climber;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * The class for the climber component. The climber has a grappling hook with a winch and a firing pin,
 * and an actuating arm to aim/ready it before firing.
 * 
 * @author Corbin
 *
 */

public class ClimberComponent
{
    private Talon winch;
    private DoubleSolenoid firingPin;
    private DoubleSolenoid arm;
    private boolean isArmExtended;
    private Encoder encoder;
    
    public ClimberComponent()
    {
        this.winch = new Talon(ElectronicsConstants.CLIMBER_WINCH_MOTOR_CHANNEL);
        
        this.firingPin = new DoubleSolenoid(
            ElectronicsConstants.PCM_A_MODULE, 
            ElectronicsConstants.CLIMBER_FIRING_PIN_CHANNEL_A, 
            ElectronicsConstants.CLIMBER_FIRING_PIN_CHANNEL_B);
        this.arm = new DoubleSolenoid(
            ElectronicsConstants.PCM_A_MODULE,
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_A, 
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_B);
        
        //this.encoder = new Encoder(ElectronicsConstants.CLIMBER_ENCODER_CHANNEL_A, ElectronicsConstants.CLIMBER_ENCODER_CHANNEL_B);
        //this.encoder.setDistancePerPulse(TuningConstants.CLIMBER_ENCODER_DISTANCE_PER_PULSE);
        this.isArmExtended = false;
    }
    
    public void stop()
    {
        this.winch.set(0.0);
        this.firingPin.set(Value.kOff);
        //this.arm.set(Value.kOff);
    }
    
    // Takes a speed and sets the winch with that speed
    public void setWinchSpeed(double motorSpeed)
    {
        this.winch.set(motorSpeed);
    }
    
    // Takes a speed and sets that to the firing pin
    public void setFiringPin(boolean extend)
    {
        if (extend)
        {
            this.firingPin.set(Value.kForward);
        }
        else
        {
            this.firingPin.set(Value.kReverse);
        }
    }
    
    // Return the value (in ticks) of the encoder
    public double getEncoderTicks()
    {
        return this.encoder.get();
    }
    
    // Return the value (in distance set) of the encoder
    public double getEncoderDistance()
    {
        return this.encoder.get();
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
        this.isArmExtended = enable;
    }

    public boolean getArmIsExtended()
    {
        return this.isArmExtended;
    }
}
