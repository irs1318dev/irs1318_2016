package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor.Color;

/**
 * Test controller.
 *
 */
public class TestController implements IController
{
    private final TestComponent component;

    /**
     * Initializes a new TestController
     * @param component to control
     */
    public TestController(TestComponent component)
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

            int red, green, blue;
            red = (int)(256 * (((double)color.getRed()) / clear));
            green = (int)(256 * (((double)color.getGreen()) / clear));
            blue = (int)(256 * (((double)color.getBlue()) / clear));

            colorString = String.format("%02X%02X%02X (%04X)", red, green, blue, clear);
        }

        if (colorString != null)
        {
            System.out.printf("Color: %s", colorString);
        }

        Integer proximity = this.component.getProximity();
        Integer ambientLight = this.component.getAmbientLight();
        if (proximity != null || ambientLight != null)
        {
            System.out.printf("Proximity: %04X, AmbientLight: %04X\n", proximity, ambientLight);
        }

        Boolean irSensed = this.component.getIRSense();
        if (irSensed != null)
        {
            System.out.printf("IR: %s\n", irSensed.toString());
        }

        Boolean sharpProximity = this.component.getSharpProximity();
        if (sharpProximity != null)
        {
            System.out.printf("WithinProximity: %s\n", sharpProximity.toString());
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
