package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor;
import org.usfirst.frc.team1318.robot.Sensors.VCNL4010ProximityALSensor;

import edu.wpi.first.wpilibj.I2C;

/**
 * A test component class. 
 * 
 */
public class TestComponent
{
    private final TCS34725LightSensor lightSensor;
    private final VCNL4010ProximityALSensor proxSensor;

    /**
     * Initializes a new TestComponent
     */
    public TestComponent()
    {
        this.lightSensor = new TCS34725LightSensor(I2C.Port.kOnboard);
        this.proxSensor = new VCNL4010ProximityALSensor(I2C.Port.kOnboard);
    }

    public TCS34725LightSensor.Color getColor()
    {
        if (this.lightSensor == null)
        {
            return null;
        }

        return this.lightSensor.readColor();
    }

    public int getProximity()
    {
        if (this.proxSensor == null)
        {
            return 0;
        }

        return this.proxSensor.getProximityValue();
    }

    public int getAmbientLight()
    {
        if (this.proxSensor == null)
        {
            return 0;
        }

        return this.proxSensor.getAmbientLightValue();
    }

    public void stop()
    {
        if (this.lightSensor != null)
        {
            this.lightSensor.stop();
        }

        if (this.proxSensor != null)
        {
            this.proxSensor.stop();
        }
    }
}
