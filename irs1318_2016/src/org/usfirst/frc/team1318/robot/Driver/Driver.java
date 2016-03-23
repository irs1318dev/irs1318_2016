package org.usfirst.frc.team1318.robot.Driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ButtonType;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmLifterMoveTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmElbowTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmShoulderTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.PIDBrakeTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.SequentialTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterKickerTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinDownTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.IntakeExtendTask;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.UserInputDevice;
import org.usfirst.frc.team1318.robot.Driver.States.AnalogOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.DigitalOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.OperationState;

/**
 * Driver that represents something that operates the robot.  This is either autonomous or teleop/user driver.
 *
 */
public abstract class Driver
{
    @SuppressWarnings("serial")
    protected Map<Operation, OperationDescription> operationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            // Operations for the drive train
            put(
                Operation.DriveTrainMoveForward,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Y));
            put(
                Operation.DriveTrainTurn,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.X));
            put(
                Operation.DriveTrainSimpleMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainUsePositionalMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainLeftPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.DriveTrainRightPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.DriveTrainSwapFrontOrientation,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));

            // Operations for the shooter
            put(
                Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.ShooterSpin,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.ShooterLowerKicker,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.ShooterExtendHood,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Simple));

            // Operations for the intake
            put(
                Operation.IntakeRotatingIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeRotatingOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    0,
                    ButtonType.Click));
            put(
                Operation.IntakeRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    180,
                    ButtonType.Click));

            // Operations for the climbing arm
            put(
                Operation.ClimbingArmExtend,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmRetract,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmElbowUp,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmElbowDown,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmShoulderUp,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmShoulderDown,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));

            // Operations for general stuff
            put(
                Operation.DisablePID,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,   
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
                    ButtonType.Click));
            put(
                Operation.EnablePID,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
                    ButtonType.Click));
            put(
                Operation.CancelBreachMacro,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_16,
                    ButtonType.Click));

            // Stinger operations
            put(
                Operation.StingerIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.StingerOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Simple));
        }
    };

    @SuppressWarnings("serial")
    protected Map<MacroOperation, MacroOperationDescription> macroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {    
            // Break mode macro
            put(
                MacroOperation.PIDBrake,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    ButtonType.Simple,
                    () -> new PIDBrakeTask(),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                    }));
            
            // Macros for shooting distance.
            put(
                MacroOperation.SpinClose,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                    }));
            put(
                MacroOperation.SpinMiddle,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                    }));
            put(
                MacroOperation.SpinFar,
                new MacroOperationDescription(
                    false,
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                        new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                        Operation.IntakeExtend,
                        Operation.IntakeRetract,
                    }));
            put(
                MacroOperation.Shoot,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                        new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                        Operation.IntakeExtend,
                        Operation.IntakeRetract,
                    }));

            // Macros for the climbing arm.
            put(
                MacroOperation.ClimbingArmRetract,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_6,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ClimbingArmElbowTask(false),
                        new ClimbingArmShoulderTask(false)),
                    new Operation[]
                    {
                        Operation.ClimbingArmElbowUp,
                        Operation.ClimbingArmElbowDown,
                        Operation.ClimbingArmShoulderUp,
                        Operation.ClimbingArmShoulderDown,
                    }));
            put(
                MacroOperation.ClimbingArmDeploy,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_7,
                    ButtonType.Toggle,
                    () -> SequentialTask.Sequence(
                        new ClimbingArmShoulderTask(true),
                        new ClimbingArmElbowTask(true)),
                    new Operation[]
                    {
                        Operation.ClimbingArmElbowUp,
                        Operation.ClimbingArmElbowDown,
                        Operation.ClimbingArmShoulderUp,
                        Operation.ClimbingArmShoulderDown,
                    }));
            put(
                MacroOperation.ClimbingArmLifterUp,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_8,
                    ButtonType.Toggle,
                    () -> new ClimbingArmLifterMoveTask(true),
                    new Operation[]
                    {
                        Operation.ClimbingArmExtend,
                        Operation.ClimbingArmRetract,
                    }));
            put(
                MacroOperation.ClimbingArmLifterDown,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_9,
                    ButtonType.Toggle,
                    () -> new ClimbingArmLifterMoveTask(false),
                    new Operation[]
                    {
                        Operation.ClimbingArmExtend,
                        Operation.ClimbingArmRetract,
                    }));
                }
    };

    protected final Map<Operation, OperationState> operationStateMap;

    /**
     * Initializes a new Driver
     */
    protected Driver()
    {
        this.operationStateMap = new HashMap<Operation, OperationState>(this.operationSchema.size());
        for (Operation operation : this.operationSchema.keySet())
        {
            this.operationStateMap.put(operation, OperationState.createFromDescription(this.operationSchema.get(operation)));
        }
    }

    /**
     * Tell the driver that some time has passed
     */
    public abstract void update();

    /**
     * Tell the driver that operation is stopping
     */
    public abstract void stop();

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(Operation digitalOperation)
    {
        OperationState state = this.operationStateMap.get(digitalOperation);
        if (!(state instanceof DigitalOperationState))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("not a digital operation!");
            }

            return false;
        }

        DigitalOperationState digitalState = (DigitalOperationState)state;
        return digitalState.getState();
    }

    /**
     * Get a double between -1.0 and 1.0 indicating the current value of the analog operation
     * @param analogOperation to get
     * @return the current value of the analog operation
     */
    public double getAnalog(Operation analogOperation)
    {
        OperationState state = this.operationStateMap.get(analogOperation);
        if (!(state instanceof AnalogOperationState))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("not an analog operation!");
            }

            return 0.0;
        }

        AnalogOperationState analogState = (AnalogOperationState)state;
        return analogState.getState();
    }
}
