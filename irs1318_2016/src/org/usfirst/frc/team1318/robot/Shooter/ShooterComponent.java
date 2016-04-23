package org.usfirst.frc.team1318.robot.Shooter;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Component for the shooter mechanism. Has a talon, and one counter, 
 * a kicker that loads the ball, an actuating hood, a targeting light, and a light to show when it is ready to fire.
 * @author Corbin
 *
 */
public class ShooterComponent
{
    private final DoubleSolenoid kicker;
    private final DoubleSolenoid hood;
    private final Talon talon;
    private final Encoder encoder;
    private final Solenoid readyLight;

    public ShooterComponent() 
    {
        
        this.kicker = new DoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
        this.hood = new DoubleSolenoid(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        this.talon = new Talon(ElectronicsConstants.SHOOTER_TALON_CHANNEL);
        this.encoder = new Encoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
        this.readyLight = new Solenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_READY_LIGHT_PORT);
    }

    public void setMotorSpeed(double speed) 
    {
        this.talon.set(speed);
    }

    public int getCounterTicks()
    {
        int counterTicks = this.encoder.get();
        return counterTicks;
    }

    public double getCounterRate() 
    {
        double counterRate = this.encoder.getRate();
        return counterRate;
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
    public void extendHood(boolean up)
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

    public void setReadyLight(boolean on)
    {
        this.readyLight.set(on);
    }

    public void stop()
    {
        this.kicker.set(Value.kOff);
        this.hood.set(Value.kOff);
        this.setMotorSpeed(0.0);
        this.readyLight.set(false);
    }
}
