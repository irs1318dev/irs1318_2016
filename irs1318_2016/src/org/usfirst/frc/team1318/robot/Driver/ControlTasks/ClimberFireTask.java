package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class ClimberFireTask extends TimedTask implements IControlTask
{
    private boolean hasArmExtended;
    
    public ClimberFireTask(double duration)
    {
        super(duration);
        this.hasArmExtended = this.getComponents().getClimberComponent().getArmIsExtended();
    }

    @Override
    public void update()
    {
        this.hasArmExtended = this.getComponents().getClimberComponent().getArmIsExtended();
        
        if (this.hasArmExtended)
        {
            this.setDigitalOperationState(Operation.ClimberFiringPinExtend, true);
        }
    }
    
    @Override
    public boolean hasCompleted()
    {
        super.hasCompleted();
        
        if(!this.hasArmExtended)
        {
            return true;
        }
        
        return false;
    }
}
