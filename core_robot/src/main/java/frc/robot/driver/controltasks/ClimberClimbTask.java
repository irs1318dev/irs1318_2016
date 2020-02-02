package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.common.IControlTask;
import frc.robot.mechanisms.ClimberMechanism;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ClimberClimbTask extends TimedTask implements IControlTask
{
    private ClimberMechanism climber;

    private double desiredDistance;
    private double currentDistance;
    private double maxSpeed;
    private double desiredSpeed;

    private boolean hasArmExtended;

    public ClimberClimbTask(double duration, double distance)
    {
        super(duration);
        this.desiredDistance = distance;
        this.hasArmExtended = false;
    }
    
    @Override
    public void begin()
    {
        this.climber = this.getInjector().getInstance(ClimberMechanism.class);
        this.hasArmExtended = this.climber.getArmIsExtended();
        if (this.hasArmExtended)
        {
            // The max speed that the climber will be traveling at the end of its distance (found by using the total distance and time
            this.maxSpeed = 2.0 * this.desiredDistance/this.duration;
        
            //this.currentDistance = this.climber.getEncoderDistance();
        
            // Speed should be a linear line, so this scales it from 0% of max at time = 0 to 100% at time = duration
            this.desiredSpeed = this.maxSpeed * this.getRatioComplete();
            this.setDigitalOperationState(DigitalOperation.ClimberWinchExtend, true);
            this.setAnalogOperationState(AnalogOperation.ClimberWinchSpeed, this.desiredSpeed);
        }
    }

    @Override
    public void update()
    {
        if (this.hasArmExtended)
        {       
            //this.currentDistance = this.climber.getEncoderDistance();
        
            // Speed should be a linear line, so this scales it from 0% of max at time = 0 to 100% at time = duration
            this.desiredSpeed = this.maxSpeed * this.getRatioComplete();
            this.setDigitalOperationState(DigitalOperation.ClimberWinchExtend, true);
            this.setAnalogOperationState(AnalogOperation.ClimberWinchSpeed, this.desiredSpeed);
        }
    }

    @Override
    public void stop()
    {
        this.setDigitalOperationState(DigitalOperation.ClimberWinchExtend, false);
        this.setAnalogOperationState(AnalogOperation.ClimberWinchSpeed, 0.0);
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(DigitalOperation.ClimberWinchExtend, false);
        this.setAnalogOperationState(AnalogOperation.ClimberWinchSpeed, 0.0);   
    }

    @Override
    public boolean hasCompleted()
    {
        //Return true if:
        return 
            // The total time interval has elapsed
            super.hasCompleted() 
            // The climber at or within a small amount of error of the desired point
            || (this.currentDistance >= this.desiredDistance - this.desiredDistance * TuningConstants.CLIMBER_ACCEPTABLE_DELTA)
            // The climber is at or over the desired climb distance
            || (this.currentDistance <= this.desiredDistance + this.desiredDistance)
            // The arm is down. This makes the macro end, and stops it from taking control of the robot until the time has elapsed.
            || (!this.hasArmExtended);
    }

}
