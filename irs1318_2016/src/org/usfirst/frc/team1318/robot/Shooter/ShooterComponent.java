package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the shooter mechanism. Has a talon, and one counter, 
 * a kicker that loads the ball, and an actuating hood.
 * @author Corbin
 *
 */
public class ShooterComponent
{
    private final DoubleSolenoid kicker;
    private final DoubleSolenoid hood;
    private final Talon talon;
    //private final Counter counter;
    
    public ShooterComponent() 
    {
        this.kicker = new DoubleSolenoid(ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
        this.hood = new DoubleSolenoid(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        this.talon = new Talon(ElectronicsConstants.SHOOTER_TALON_CHANNEL);
        //this.counter = new Counter(ElectronicsConstants.SHOOTER_COUNTER_CHANNEL);
        //this.counter.setUpDownCounterMode();
        //this.counter.setDistancePerPulse(1.0);
    }
    
    public void setMotorSpeed(double speed) 
    {
        this.talon.set(speed);
    }
    
    public int getCounterTicks()
    {
        //int counterTicks = this.counter.get();
        //return counterTicks;
        return 0;
    }
    
    public double getCounterRate() 
    {
        //double counterRate = this.counter.getRate();
        //return counterRate;
        return 0.0;
    }
    
    /**
     * Actuates the kicker. 
     * 
     * @param up - true is up, false is down
     */
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
    
    /**
     * Extends or retracts the hood.
     * 
     * @param up - true extends, false retracts
     */
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
