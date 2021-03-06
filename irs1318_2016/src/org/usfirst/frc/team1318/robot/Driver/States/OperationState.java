package org.usfirst.frc.team1318.robot.Driver.States;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.OperationDescription;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The state of a current operation.
 *
 */
public abstract class OperationState
{
    private final OperationDescription description;

    protected OperationState(OperationDescription description)
    {
        this.description = description;
    }

    protected OperationDescription getDescription()
    {
        return this.description;
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    public abstract void setIsInterrupted(boolean enable);

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    public abstract boolean getIsInterrupted();

    /**
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param components to update from
     * @return true if there was any active user input that triggered a state change
     */
    public abstract boolean checkInput(Joystick driver, Joystick coDriver, ComponentManager components);

    /**
     * Create the state corresponding to the description
     * @param description to use for creating the state
     * @return state corresponding to the description
     */
    public static OperationState createFromDescription(OperationDescription description)
    {
        if (description instanceof AnalogOperationDescription)
        {
            return new AnalogOperationState((AnalogOperationDescription)description);
        }
        else if (description instanceof DigitalOperationDescription)
        {
            return new DigitalOperationState((DigitalOperationDescription)description);
        }

        if (TuningConstants.THROW_EXCEPTIONS)
        {
            throw new RuntimeException("unknown type of description " + description.getClass().getName());
        }

        return null;
    }
}
