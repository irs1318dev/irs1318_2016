package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725ColorSensor.Color;

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
            System.out.printf("Color: %s\n", colorString);
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
