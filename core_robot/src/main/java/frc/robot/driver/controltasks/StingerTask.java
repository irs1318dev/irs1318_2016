
package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Stinger Macro, used to put stinger in or out for a duration
 * @author Preston
 *
 */
public class StingerTask extends TimedTask implements IControlTask
{
    
    private boolean out;
    /**
     * Initializes a new StingerTask
     * @param duration to perform the task in seconds
     * @param out - true puts the stinger out, false puts it in
     */
    public StingerTask(double duration, boolean out)
    {
        super(duration);
        this.out = out;
    }

    @Override
    public void begin()
    {
        super.begin();
        
        if (out)
        {
            this.setDigitalOperationState(DigitalOperation.StingerOut, true); 
        }
        else
        {
            this.setDigitalOperationState(DigitalOperation.StingerIn, true);
        }
    }
    
    @Override
    public void update()
    {      
        if (out)
        {
            this.setDigitalOperationState(DigitalOperation.StingerOut, true); 
        }
        else
        {
            this.setDigitalOperationState(DigitalOperation.StingerIn, true);
        }
    }
    
    /*
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {   
        super.stop();
        
        this.setDigitalOperationState(DigitalOperation.StingerIn, false);
        this.setDigitalOperationState(DigitalOperation.StingerOut, false);
    }
    
    /*
     * End the current task 
     */
    @Override
    public void end()
    {
       super.end();

       this.setDigitalOperationState(DigitalOperation.StingerIn, false);
       this.setDigitalOperationState(DigitalOperation.StingerOut, false);
    }
}