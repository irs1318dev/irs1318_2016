package org.usfirst.frc.team1318.robot.Sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class IRSensor
{
    private final DigitalInput sensor;
    private final DigitalOutput emitter;

    public IRSensor(int inChannel, int outChannel)
    {
        this.sensor = new DigitalInput(inChannel);
        this.emitter = new DigitalOutput(outChannel);
    }
    
    public void start()
    {
        this.emitter.set(true);
    }
    
    public void stop()
    {
        this.emitter.set(false);
    }

    public boolean get()
    {
        return this.sensor.get();
    }
}
