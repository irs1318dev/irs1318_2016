package org.usfirst.frc.team1318.robot.Stinger;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Talon;

public class StingerComponent
{
    private final Talon talon;
    
    public StingerComponent()
    {
        this.talon = new Talon(ElectronicsConstants.STINGER_MOTOR_CHANNEL);
    }
    
    public void setMotor(double speed)
    {
        this.talon.set(-speed);
    }
    
    public void stop()
    {
        this.talon.set(0.0);
    }
}
