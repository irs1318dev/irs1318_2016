package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the shooter mechanism. Has one talon, and one encoder.
 * @author Corbin
 *
 */

public class ShooterComponent
{
    private Talon talon;
    private Encoder encoder;
    
    public ShooterComponent() 
    {
        this.talon = new Talon(ElectronicsConstants.SHOOTER_MOTOR_CHANNEL);
        this.encoder = new Encoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
    }
    
    public void setMotorSpeed(double speed) 
    {
        this.talon.set(speed);
    }
    
    public double getEncoderTicks()
    {
        return this.encoder.get();
    }
}
