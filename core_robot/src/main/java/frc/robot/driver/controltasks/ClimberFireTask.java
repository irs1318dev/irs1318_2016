package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ClimberFireTask extends TimedTask implements IControlTask
{
    private boolean hasArmExtended;
    
    public ClimberFireTask(double duration)
    {
        super(duration);
//        this.hasArmExtended = this.getComponents().getClimberComponent().getArmIsExtended();
    }

    @Override
    public void update()
    {
//        this.hasArmExtended = this.getComponents().getClimberComponent().getArmIsExtended();
        
        if (this.hasArmExtended)
        {
            this.setDigitalOperationState(DigitalOperation.ClimberFiringPinExtend, true);
        }
    }
    
    @Override
    public boolean hasCompleted()
    {
        // Return true if the time has elapsed, or if the arm is not extended. 
        // The arm is here to make the macro end early if the arm is down, and thus not take up all of the time.
        return super.hasCompleted() || !this.hasArmExtended;
    }
}