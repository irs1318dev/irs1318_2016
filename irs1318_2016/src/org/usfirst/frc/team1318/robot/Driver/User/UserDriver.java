package org.usfirst.frc.team1318.robot.Driver.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.Common.SetHelper;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.JoystickButtonConstants;
import org.usfirst.frc.team1318.robot.Driver.MacroOperation;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Driver.States.MacroOperationState;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Driver for teleop mode.  User driver translates current state and joystick state information into
 * the specific actions that should be taken by the robot.
 * 
 */
public class UserDriver extends Driver
{
    private final Joystick joystickDriver;
    private final Joystick joystickCoDriver;

    private final Map<MacroOperation, MacroOperationState> macroStateMap;
    private final ComponentManager components;

    /**
     * Initializes a new UserDriver
     * @param components to utilize within the robot
     */
    public UserDriver(ComponentManager components)
    {
        super();

        this.joystickDriver = new Joystick(JoystickButtonConstants.JOYSTICK_DRIVER_PORT);
        this.joystickCoDriver = new Joystick(JoystickButtonConstants.JOYSTICK_CO_DRIVER_PORT);

        this.macroStateMap = new HashMap<MacroOperation, MacroOperationState>();
        this.components = components;

        for (MacroOperation macroOperation : this.macroSchema.keySet())
        {
            this.macroStateMap.put(
                macroOperation,
                new MacroOperationState(
                    this.macroSchema.get(macroOperation),
                    this.operationStateMap,
                    this.components));
        }
    }

    /**
     * Tell the driver that some time has passed
     */
    @Override
    public void update()
    {
        // keep track of macros that were running before we checked user input...
        Set<MacroOperation> previouslyActiveMacroOperations = new HashSet<MacroOperation>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            MacroOperationState macroState = this.macroStateMap.get(macroOperation);
            if (macroState.getIsActive())
            {
                previouslyActiveMacroOperations.add(macroOperation);
            }
        }

        // check user inputs for various operations (non-macro) and keep track of:
        // operations that were interrupted already, and operations that were modified by user input in this update
        Set<Operation> modifiedOperations = new HashSet<Operation>();
        Set<Operation> interruptedOperations = new HashSet<Operation>();
        for (Operation operation : this.operationStateMap.keySet())
        {
            boolean receivedInput = this.operationStateMap.get(operation).checkUserInput(this.joystickDriver, this.joystickCoDriver);
            if (receivedInput)
            {
                modifiedOperations.add(operation);
            }

            if (this.operationStateMap.get(operation).getIsInterrupted())
            {
                interruptedOperations.add(operation);
            }
        }

        // check user inputs for various macro operations
        // also keep track of modified and active macro operations, and how macro operations and operations link together
        Set<MacroOperation> activeMacroOperations = new HashSet<MacroOperation>();
        Map<Operation, Set<MacroOperation>> activeMacroOperationMap = new HashMap<Operation, Set<MacroOperation>>();
        Set<MacroOperation> modifiedMacroOperations = new HashSet<MacroOperation>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            MacroOperationState macroState = this.macroStateMap.get(macroOperation);
            boolean modifiedMacro = macroState.checkUserInput(this.joystickDriver, this.joystickCoDriver);
            if (modifiedMacro)
            {
                modifiedMacroOperations.add(macroOperation);
            }

            if (macroState.getIsActive())
            {
                activeMacroOperations.add(macroOperation);

                for (Operation affectedOperation : macroState.getAffectedOperations())
                {
                    Set<MacroOperation> relevantMacroOperations = activeMacroOperationMap.get(affectedOperation);
                    if (relevantMacroOperations == null)
                    {
                        relevantMacroOperations = new HashSet<MacroOperation>();
                        activeMacroOperationMap.put(affectedOperation, relevantMacroOperations);
                    }

                    relevantMacroOperations.add(macroOperation);
                }
            }
        }

        // Determine the list of macro operations to cancel.  Only keep macros that:
        // 1. have not been usurped by a user action
        // 2. have not been usurped by a new macro (i.e. that was started in this round)
        // 3. are new macros that do not overlap with other new macros
        Set<MacroOperation> macroOperationsToCancel = new HashSet<MacroOperation>();
        for (Operation operation : activeMacroOperationMap.keySet())
        {
            Set<MacroOperation> relevantMacroOperations = activeMacroOperationMap.get(operation);
            if (modifiedOperations.contains(operation))
            {
                // disobeys rule #1:
                // (macro usurped by user action)
                macroOperationsToCancel.addAll(relevantMacroOperations);
            }
            else if (relevantMacroOperations.size() > 1)
            {
                Set<MacroOperation> newRelevantMacroOperations = SetHelper.<MacroOperation> RelativeComplement(
                    previouslyActiveMacroOperations, relevantMacroOperations);
                if (newRelevantMacroOperations.size() > 1)
                {
                    // disobeys rule #3:
                    // (there are 2 or more active macros that weren't previously active)
                    macroOperationsToCancel.addAll(relevantMacroOperations);
                }
                else
                {
                    // some disobey rule #2 (remove only those that were previously active, and not the 1 that is newly active...)
                    macroOperationsToCancel.addAll(SetHelper.<MacroOperation> RelativeComplement(newRelevantMacroOperations,
                        relevantMacroOperations));
                }
            }
        }

        // cancel macros that didn't follow the rules list above
        for (MacroOperation macroOperationToCancel : macroOperationsToCancel)
        {
            this.macroStateMap.get(macroOperationToCancel).setIsInterrupted(true);
        }

        // determine which operations should actually be interrupted by our new macro:
        Set<Operation> desiredInterruptedOperations = new HashSet<Operation>();
        for (MacroOperation macroOperationToKeep : SetHelper.<MacroOperation> RelativeComplement(macroOperationsToCancel,
            activeMacroOperations))
        {
            desiredInterruptedOperations.addAll(Arrays.asList(this.macroSchema.get(macroOperationToKeep).getAffectedOperations()));
        }

        // interrupt operations that are not interrupted that should be:
        for (Operation operationToInterrupt : SetHelper.<Operation> RelativeComplement(interruptedOperations, desiredInterruptedOperations))
        {
            this.operationStateMap.get(operationToInterrupt).setIsInterrupted(true);
        }

        // clear interruption for operations that are interrupted that should not be:
        for (Operation operationToUnInterrupt : SetHelper.<Operation> RelativeComplement(desiredInterruptedOperations,
            interruptedOperations))
        {
            this.operationStateMap.get(operationToUnInterrupt).setIsInterrupted(false);
        }

        // run all of the macros:
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            this.macroStateMap.get(macroOperation).run();
        }
    }

    /**
     * Tell the driver that operation is stopping
     */
    @Override
    public void stop()
    {
    }
}
