package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.Sensors.IRSensor;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor.Color;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor.Gain;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor.IntegrationTime;
import org.usfirst.frc.team1318.robot.Sensors.VCNL4010ProximityALSensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;

/**
 * A test component class. 
 * 
 */
public class TestComponent
{
    private final TCS34725LightSensor lightSensor;
    private final VCNL4010ProximityALSensor proxSensor;
    private final DigitalInput sharpSensor;
    private final IRSensor irSensor;

    /**
     * Initializes a new TestComponent
     */
    public TestComponent()
    {
        this.lightSensor = null; //new TCS34725LightSensor(I2C.Port.kOnboard, IntegrationTime.Time101MS, Gain.X1);
        this.proxSensor = null; //new VCNL4010ProximityALSensor(I2C.Port.kOnboard);
        this.sharpSensor = new DigitalInput(ElectronicsConstants.TEST_SENSOR_SHARP);
        this.irSensor = null; //new IRSensor(ElectronicsConstants.TEST_SENSOR_IR_IN, ElectronicsConstants.TEST_SENSOR_IR_OUT);
    }

    public void start()
    {
        if (this.lightSensor != null)
        {
            this.lightSensor.start();
        }
        
        if (this.proxSensor != null)
        {
            this.proxSensor.start();
        }
        
        if (this.irSensor != null)
        {
            this.irSensor.start();
        }
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
        
        if (this.irSensor != null)
        {
            this.irSensor.stop();
        }
    }

    public Color getColor()
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

    public boolean getSharpProximity()
    {
        if (this.sharpSensor == null)
        {
            return false;
        }
        
        // sharp digital distance sensor returns the reverse of what I'd expect (true when far away, false when close)
        return !this.sharpSensor.get();
    }

    public boolean getIRSense()
    {
        if (this.irSensor == null)
        {
            return false;
        }

        return this.irSensor.get();
    }
}
