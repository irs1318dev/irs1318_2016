package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.Talon;

public class ClimbingArmComponent
{
    private Talon motor;
    
    
    public ClimbingArmComponent(){
        this.motor = new Talon(ElectronicsConstants.CLIMBING_ARM_MOTOR_CHANNEL);
    }
    
    public void stop(){
        this.motor.set(0.0);
    }
    
    //takes a speed and sets the motor with that speedS
    public void setClimbingSpeed(double motorSpeed){
        this.motor.set(motorSpeed);
    }
}
