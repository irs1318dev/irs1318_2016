package org.usfirst.frc.team1318.robot.Driver.States;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.Driver.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ClickButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.IButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.SimpleButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ToggleButton;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.DigitalOperationDescription;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The state of the current digital operation.
 *
 */
public class DigitalOperationState extends OperationState
{
    private final IButton button;
    private boolean isInterrupted;
    private boolean interruptValue;

    public DigitalOperationState(DigitalOperationDescription description)
    {
        super(description);

        this.isInterrupted = false;
        this.interruptValue = false;
        switch (description.getButtonType())
        {
            case Simple:
                this.button = new SimpleButton();
                break;

            case Click:
                this.button = new ClickButton();
                break;

            case Toggle:
                this.button = new ToggleButton();
                break;

            default:
                throw new RuntimeException("unexpected button type " + description.getButtonType().toString());
        }
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    @Override
    public void setIsInterrupted(boolean enable)
    {
        this.isInterrupted = enable;
        if (enable)
        {
            this.interruptValue = false;
        }
    }

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    @Override
    public boolean getIsInterrupted()
    {
        return this.isInterrupted;
    }

    /**
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param components to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(Joystick driver, Joystick coDriver, ComponentManager components)
    {
        DigitalOperationDescription description = (DigitalOperationDescription)this.getDescription();

        Joystick relevantJoystick;
        UserInputDeviceButton relevantButton;
        switch (description.getUserInputDevice())
        {
            case None:
                return false;

            case Driver:
                relevantJoystick = driver;
                break;

            case CoDriver:
                relevantJoystick = coDriver;
                break;

            case Sensor:
                relevantJoystick = null;

            default:
                throw new RuntimeException("unexpected user input device " + description.getUserInputDevice().toString());
        }

        boolean buttonPressed;
        if (relevantJoystick != null)
        {
            // find the appropriate button and grab the value from the relevant joystick
            relevantButton = description.getUserInputDeviceButton();

            if (relevantButton == UserInputDeviceButton.JOYSTICK_POV)
            {
                buttonPressed = relevantJoystick.getPOV() == description.getUserInputDevicePovValue();
            }
            else
            {
                buttonPressed = relevantJoystick.getRawButton(relevantButton.Value);
            }
        }
        else
        {
            // grab the appropriate sensor output.
            // e.g.: if (description.getSensor() == DigitalSensor.None) ...
            buttonPressed = false;
        }

        this.button.updateState(buttonPressed);
        return buttonPressed;
    }

    public boolean getState()
    {
        if (this.isInterrupted)
        {
            return this.interruptValue;
        }

        return this.button.isActivated();
    }

    public void setInterruptState(boolean value)
    {
        this.interruptValue = value;
    }
}
