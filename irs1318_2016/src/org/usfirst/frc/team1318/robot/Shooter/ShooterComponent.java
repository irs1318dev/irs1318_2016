package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the shooter mechanism. Has one talon, and one counter.
 * @author Corbin
 *
 */
public class ShooterComponent
{
    private final DoubleSolenoid kicker;
    private final Talon talon;
    private final Counter counter;
    
    public ShooterComponent() 
    {
        this.kicker = new DoubleSolenoid(ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
        this.talon = new Talon(ElectronicsConstants.SHOOTER_MOTOR_CHANNEL);
        this.counter = new Counter(ElectronicsConstants.SHOOTER_COUNTER_CHANNEL);
        this.counter.setUpDownCounterMode();
    }
    
    public void setMotorSpeed(double speed) 
    {
        this.talon.set(speed);
    }
    
    public double getCounterTicks() 
    {
        return this.counter.getRate();
    }
    
    public void kick(boolean up)
    {
        if (up)
        {
            this.kicker.set(Value.kForward);
        }
        else
        {
            this.kicker.set(Value.kReverse);
        }
    }
    
    public void stop()
    {
        this.kicker.set(Value.kOff);
        this.setMotorSpeed(0.0);
    }
}
