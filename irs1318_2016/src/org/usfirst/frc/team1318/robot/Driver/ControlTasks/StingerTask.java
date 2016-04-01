
package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.Timer;

/**
 * Stinger Macro, used to put stinger down or up
 * @author Preston
 *
 */
public class StingerTask extends TimedTask
{
    
    
    /**
     * Initializes a new StingerTask
     * @param duration to perform the task in seconds
     */
    public StingerTask(double duration)
    {
        super(duration);
    }

    @Override
    public void begin()
    {
        super.begin();
        
        this.setDigitalOperationState(Operation.StingerOut, true);
    }

    
    /*
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {   
        super.stop();
        
        this.setDigitalOperationState(Operation.StingerIn, true);
    }
    
    /*
     * End the current task 
     */
    @Override
    public void end()
    {
       super.end();
       
       this.setDigitalOperationState(Operation.StingerIn, true);
    }

    @Override
    public void update()
    {      
    }
}