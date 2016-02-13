package org.usfirst.frc.team1318.robot.SensorManager;

import org.usfirst.frc.team1318.robot.Common.DashboardLogger;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor.Color;

/**
 * SensorManager controller.
 *
 */
public class SensorManagerController implements IController
{
    private final SensorManagerComponent component;

    /**
     * Initializes a new SensorManagerController
     * @param component to control
     */
    public SensorManagerController(SensorManagerComponent component)
    {
        this.component = component;
    }

    /**
     * set the driver that the controller should use
     * @param driver to use
     */
    @Override
    public void setDriver(Driver driver)
    {
        // not needed for this controller
        this.component.start();
    }

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant component
     */
    @Override
    public void update()
    {
        String colorString = null;
        Color color = this.component.getColor();
        if (color != null)
        {
            int clear = color.getClear();
            if (clear == 0)
            {
                clear = 1;
            }

            int red = color.getRed(); 
            int green = color.getGreen();
            int blue = color.getBlue();

            int adjustedRed = (int)(256 * (((double)red) / clear));
            int adjustedGreen = (int)(256 * (((double)green) / clear));
            int adjustedBlue = (int)(256 * (((double)blue) / clear));
            
            colorString = String.format("R: %04X G: %04X B: %04X (%04X) - %02X%02X%02X", red, green, blue, clear, adjustedRed, adjustedGreen, adjustedBlue);
        }

        if (colorString != null)
        {
            DashboardLogger.putString("Color", colorString);
        }

        Integer proximity = this.component.getProximity();
        Integer ambientLight = this.component.getAmbientLight();
        if (proximity != null || ambientLight != null)
        {
            DashboardLogger.putInteger("Proximity", proximity, "%04X");
            DashboardLogger.putInteger("AmbientLight", ambientLight, "%04X");
        }

        Boolean irSensed = this.component.getIRSense();
        if (irSensed != null)
        {
            DashboardLogger.putBoolean("IR", irSensed);
        }

        Boolean sharpProximity = this.component.getSharpProximity();
        if (sharpProximity != null)
        {
            DashboardLogger.putBoolean("WithinProximity", irSensed);
        }
    }

    /**
     * stop the relevant component
     */
    @Override
    public void stop()
    {
        this.component.stop();
    }
}