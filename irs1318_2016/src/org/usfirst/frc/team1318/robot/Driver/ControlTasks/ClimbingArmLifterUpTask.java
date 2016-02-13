
package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * Climbing macro. Can be used to put the shoulder up or down.
 * @author Corbin
 *
 */
public class ClimbingArmLifterUpTask extends ControlTaskBase
{
    private boolean extend;
    
    // True extends, and false retracts.
    public ClimbingArmLifterUpTask(boolean extend)
    {
        this.extend = extend;
    }

    @Override
    public void begin()
    {
        // If extension is requested, set the appropriate operation to true, and do a check on the opposing operation.
        if (extend)
        {
            this.setDigitalOperationState(Operation.ClimbingArmExtend, true);
            this.setDigitalOperationState(Operation.ClimbingArmRetract, false);
        }
        // If retraction is requested, set the appropriate operation to true, and do a check on the opposing operation.
        else
        {
            this.setDigitalOperationState(Operation.ClimbingArmExtend, false);
            this.setDigitalOperationState(Operation.ClimbingArmRetract, true);
        }
    }

    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.ClimbingArmExtend, false);
        this.setDigitalOperationState(Operation.ClimbingArmRetract, false);
    }

    @Override
    public void end()
    {
        this.setDigitalOperationState(Operation.ClimbingArmExtend, false);
        this.setDigitalOperationState(Operation.ClimbingArmRetract, false);
    }

    @Override
    public boolean hasCompleted()
    {
        // If extending, checks to see if the top limit switch has been triggered.
        if(extend)
        {
            return this.getComponents().getClimbingArmComponent().getTopLimitSwitch();
        }
        // If retracting, checks to see if the bottom limit switch has been triggered.
        else
        {
            return this.getComponents().getClimbingArmComponent().getBottomLimitSwitch();
        }
    }

    @Override
    public void update()
    {        
    }
}
