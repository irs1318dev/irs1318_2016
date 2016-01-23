package org.usfirst.frc.team1318.robot.TestMechanism;

import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;

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
    }

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant component
     */
    @Override
    public void update()
    {
    }

    /**
     * stop the relevant component
     */
    @Override
    public void stop()
    {
    }
}
