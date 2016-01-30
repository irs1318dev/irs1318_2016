package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Sensors.TCS34725LightSensor;

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
        String colorString = "";
        TCS34725LightSensor.Color color = this.component.getColor();
        if (color != null)
        {
            colorString += String.format("%04X|%04X|%04X|%04X", color.getRed(), color.getGreen(), color.getBlue(), color.getClear());
        }

        int proximity = this.component.getProximity();
        int ambientLight = this.component.getAmbientLight();
        System.out.printf("Color: %s, Proximity: %04X, AmbientLight: %04X\n", colorString, proximity, ambientLight);
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
