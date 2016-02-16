package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the shooter mechanism. Has two talons, and one counter, 
 * a kicker that loads the ball, and an actuating hood.
 * @author Corbin
 *
 */
public class ShooterComponent
{
    private final DoubleSolenoid kicker;
    private final DoubleSolenoid hood;
    private final Talon leftTalon;
    private final Talon rightTalon;
    private final Counter counter;
    
    public ShooterComponent() 
    {
        this.kicker = new DoubleSolenoid(ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
        this.hood = new DoubleSolenoid(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        this.leftTalon = new Talon(ElectronicsConstants.SHOOTER_LEFT_TALON_CHANNEL);
        this.rightTalon = new Talon(ElectronicsConstants.SHOOTER_RIGHT_TALON_CHANNEL);
        this.counter = new Counter(ElectronicsConstants.SHOOTER_COUNTER_CHANNEL);
        this.counter.setUpDownCounterMode();
    }
    
    public void setMotorSpeed(double speed) 
    {
        this.leftTalon.set(speed);
        this.rightTalon.set(speed);
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
    
    public void hood(boolean up)
    {
        if (up)
        {
            this.hood.set(Value.kForward);
        }
        else
        {
            this.hood.set(Value.kReverse);
        }
    }
    
    public void stop()
    {
        this.kicker.set(Value.kOff);
        this.hood.set(Value.kOff);
        this.setMotorSpeed(0.0);
    }
}
