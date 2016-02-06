package org.usfirst.frc.team1318.robot.Intake;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.HardwareConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * Component for the intake mechanism.
 * @author Nathan
 */

public class IntakeComponent
{
    private final Talon talon;
    
    
    public IntakeComponent(){
        this.talon = new Talon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
    }
    
    //set speed of the intake
    public void setSpeed(double speed){
        this.talon.set(speed);
    }
    
    
}
