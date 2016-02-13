package org.usfirst.frc.team1318.robot.SensorManager;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.Sensors.IRSensor;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor.Color;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor.Gain;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor.IntegrationTime;
import org.usfirst.frc.team1318.robot.Sensors.VCNL4010ProximityALSensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;

/**
 * A sensor manager component class. 
 * 
 */
public class SensorManagerComponent
{
    private final TCS34725ColorSensor lightSensor;
    private final VCNL4010ProximityALSensor proxSensor;
    private final DigitalInput sharpSensor;
    private final DigitalInput irSensor;

    /**
     * Initializes a new SensorManagerComponent
     */
    public SensorManagerComponent()
    {
        this.lightSensor = null; //new TCS34725ColorSensor(I2C.Port.kOnboard, IntegrationTime.Time101MS, Gain.X1);
        this.proxSensor = null; //new VCNL4010ProximityALSensor(I2C.Port.kOnboard);
        this.sharpSensor = null; //new DigitalInput(ElectronicsConstants.TEST_SENSOR_SHARP);
        this.irSensor = null; //new DigitalInput(ElectronicsConstants.TEST_SENSOR_IR_IN);
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

    public Color getColor()
    {
        if (this.lightSensor == null)
        {
            return null;
        }

        return this.lightSensor.readColor();
    }

    public Integer getProximity()
    {
        if (this.proxSensor == null)
        {
            return null;
        }

        return this.proxSensor.getProximityValue();
    }

    public Integer getAmbientLight()
    {
        if (this.proxSensor == null)
        {
            return null;
        }

        return this.proxSensor.getAmbientLightValue();
    }

    public Boolean getSharpProximity()
    {
        if (this.sharpSensor == null)
        {
            return null;
        }
        
        // sharp digital distance sensor returns the reverse of what I'd expect (true when far away, false when close)
        return !this.sharpSensor.get();
    }

    public Boolean getIRSense()
    {
        if (this.irSensor == null)
        {
            return null;
        }

        // IR sensor returns the reverse of what I'd expect (false when IR reflection detected, true otherwise)
        return !this.irSensor.get();
    }
}