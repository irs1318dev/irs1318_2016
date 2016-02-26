package org.usfirst.frc.team1318.robot.Driver.States;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.AnalogOperationDescription;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

/**
 * The state of the current analog operation.
 *
 */
public class AnalogOperationState extends OperationState
{
    private double currentValue;
    private double interruptValue;
    private boolean isInterrupted;
    private boolean macroOverride;

    public AnalogOperationState(AnalogOperationDescription description)
    {
        super(description);

        this.currentValue = 0.0;
        this.interruptValue = 0.0;
        this.isInterrupted = false;
        this.macroOverride = false;
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
            this.interruptValue = 0.0;
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
        AnalogOperationDescription description = (AnalogOperationDescription)this.getDescription();

        Joystick relevantJoystick;
        AxisType relevantAxis;
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

        double newValue;
        double oldValue = this.currentValue;
        // Return false if override is desired to stop the change of currentValue from what it was set to by the macro
        if(this.macroOverride)
        {
            return false;
        }
        else if (relevantJoystick != null)
        {
            boolean invert = false;
            switch (description.getUserInputDeviceAxis())
            {
                case None:
                    return false;

                case X:
                    invert = ElectronicsConstants.INVERT_X_AXIS;
                    relevantAxis = AxisType.kX;
                    break;

                case Y:
                    invert = ElectronicsConstants.INVERT_Y_AXIS;
                    relevantAxis = AxisType.kY;
                    break;

                case Z:
                    relevantAxis = AxisType.kZ;
                    break;

                case Twist:
                    relevantAxis = AxisType.kTwist;
                    break;

                case Throttle:
                    relevantAxis = AxisType.kThrottle;
                    break;

                default:
                    throw new RuntimeException("unknown axis type " + description.getUserInputDeviceAxis());
            }

            newValue = relevantJoystick.getAxis(relevantAxis);
            if (invert)
            {
                newValue *= -1.0;
            }
        }
        else
        {
         // grab the appropriate sensor output.
            // e.g.: if (description.getSensor() == AnalogSensor.None) ...
            newValue = 0.0;
        }

        this.currentValue = newValue;
        return this.currentValue != oldValue;
    }

    public double getState()
    {
        if (this.isInterrupted)
        {
            return this.interruptValue;
        }

        return this.currentValue;
    }
    
    /**
     * Set the current and interrupted value. 
     * @param value of true sets overrideValue and interruptValue to true
     */
    public void setState(double value)
    {
        this.currentValue = value;
        this.interruptValue = value;
    }

    public void setInterruptState(double value)
    {
        this.interruptValue = value;
    }
    
    /**
     * Set the value of macroOveride (which represents the desire for a value to persist after the end of a macro)
     * @param value of true allows the macro to override the currentValue
     */
    public void macroOverride(boolean value)
    {
        this.macroOverride = value;
    }
}
