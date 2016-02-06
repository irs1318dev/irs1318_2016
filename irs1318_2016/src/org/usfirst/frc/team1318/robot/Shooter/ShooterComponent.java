package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the shooter mechanism. Has one talon, and one counter.
 * @author Corbin
 *
 */

public class ShooterComponent
{
    private Talon talon;
    //private Encoder encoder;
    private Counter counter;
    
    public ShooterComponent() 
    {
        this.talon = new Talon(ElectronicsConstants.SHOOTER_MOTOR_CHANNEL);
        //this.encoder = new Encoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
        this.counter = new Counter(ElectronicsConstants.SHOOTER_COUNTER_CHANNEL);
    }
    
    public void setMotorSpeed(double speed) 
    {
        this.talon.set(speed);
    }
    
    public double getCounterTicks() 
    {
        return this.counter.getRate();
    }
    
    /*
    public int getEncoderTicks()
    {
        return this.encoder.get();
    }
    
    public double getShooterVelocity()
    {
        // This should be the velocity of the shooter wheel
        return this.encoder.getRate();
    }
    */
}
