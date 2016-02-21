package org.usfirst.frc.team1318.robot.Driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ButtonType;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.BreachDrawbridgeTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.BreachPortcullisTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmLifterMoveTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmElbowTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmShoulderTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DefenseArmPositionTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveDistanceTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveRouteTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.IntakePositionTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.SequentialTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterLowerKickerTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterKickTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.TurnTask;
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

            // Operations for the defense arm
            put(
                Operation.DefenseArmMaxFrontPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmHorizontalFrontPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.DefenseArmUpForwardPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmUpPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmMaxBackPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmMoveForward,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmMoveBack,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmTakePositionInput,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmSetAngle,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.DefenseArmIgnoreSensors,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_13,
                    ButtonType.Click));
            put(
                Operation.DefenseArmUseSensors,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_14,
                    ButtonType.Click));
            put(
                Operation.DisableDefenseArmPID,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_15,
                    ButtonType.Click));
            put(
                Operation.EnableDefenseArmPID,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_10,
                    ButtonType.Click));

            // Operations for the shooter
            put(
                Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.ShooterSpin,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
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
        }
    };

    @SuppressWarnings("serial")
    protected Map<MacroOperation, MacroOperationDescription> macroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {            
            // Breaching macros.
            put(
                MacroOperation.BreachPortcullis,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_PORTCULLIS_BREACH_APPROACH_POSITION),
                            new DriveDistanceTask(TuningConstants.PORTCULLIS_OUTER_WORKS_DISTANCE, false)),
                        new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_PORTCULLIS_BREACH_CAPTURE_POSITION),
                        new BreachPortcullisTask()),
                    new Operation[]
                    {
                        Operation.DriveTrainRightPosition, 
                        Operation.DriveTrainLeftPosition, 
                        Operation.DefenseArmMaxFrontPosition, 
                        Operation.DriveTrainUsePositionalMode, 
                        Operation.DefenseArmTakePositionInput, 
                        Operation.DefenseArmSetAngle,
                        Operation.CancelBreachMacro,
                    }));
            put(
                MacroOperation.BreachSallyPort,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_3,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new IntakePositionTask(false),
                            new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_SALLY_PORT_APPROACH_POSITION),
                            new DriveDistanceTask(-TuningConstants.SALLY_PORT_OUTER_WORKS_DRIVE_DISTANCE)),
                        new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_SALLY_PORT_APPROACH_POSITION + Math.PI * 1.0/16.0),
                        new DriveRouteTask(
                            (t) -> 0.35 * Math.PI * TuningConstants.SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS * t,
                            (t) -> 0.35 * Math.PI * (TuningConstants.SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS + HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE) * t,
                            2.0),
                        new DriveRouteTask(
                            (t) -> -0.35 * Math.PI * (TuningConstants.SALLY_PORT_BREACH_FORWARD_ARC_RADIUS + HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE) * t,
                            (t) -> -0.35 * Math.PI * TuningConstants.SALLY_PORT_BREACH_FORWARD_ARC_RADIUS * t,
                            2.0),
                        new TurnTask(-90),
                        new DriveDistanceTask(TuningConstants.SALLY_PORT_BREACH_FINAL_CHARGE_DISTANCE)),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode,
                        Operation.DriveTrainRightPosition,
                        Operation.DriveTrainLeftPosition,
                        Operation.DefenseArmMaxFrontPosition,
                        Operation.DefenseArmTakePositionInput,
                        Operation.DefenseArmSetAngle,
                        Operation.CancelBreachMacro,
                    }));
            put(
                MacroOperation.BreachDrawbridge,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver, 
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_4,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_DRAWBRIDGE_APPROACH_POSITION),
                            new DriveDistanceTask(TuningConstants.DRAWBRIDGE_OUTER_WORKS_DISTANCE)),
                        new DefenseArmPositionTask(HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION),
                        new BreachDrawbridgeTask()),
                new Operation[]
                {
                    Operation.DriveTrainUsePositionalMode,
                    Operation.DriveTrainLeftPosition,
                    Operation.DriveTrainRightPosition,
                    Operation.DefenseArmMaxFrontPosition,
                    Operation.DefenseArmTakePositionInput,
                    Operation.DefenseArmSetAngle,
                    Operation.CancelBreachMacro
                }));
            put(
                MacroOperation.BreachChevalDeFrise,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver, 
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_5,
                    () -> SequentialTask.Sequence(
                        new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_CHEVAL_DE_FRISE_APPROACH_POSITION),
                        new DriveDistanceTask(TuningConstants.CHEVAL_DE_FRISE_OUTER_WORKS_DISTANCE),
                        new DefenseArmPositionTask(HardwareConstants.DEFENSE_ARM_MAX_FRONT_POSITION),
                        ConcurrentTask.AllTasks(
                            new DriveDistanceTask(TuningConstants.CHEVAL_DE_FRISE_HALF_BREACH_DISTANCE),
                            new DefenseArmPositionTask(TuningConstants.DEFENSE_ARM_CHEVAL_DE_FRISE_CAPTURE_POSITION)),
                        new DriveDistanceTask(TuningConstants.CHEVAL_DE_FRISE_REMAINING_BREACH_DISTANCE)),
                new Operation[]
                {
                    Operation.DriveTrainUsePositionalMode,
                    Operation.DriveTrainLeftPosition,
                    Operation.DriveTrainRightPosition,
                    Operation.DefenseArmTakePositionInput,
                    Operation.DefenseArmSetAngle,
                    Operation.CancelBreachMacro
                }));

            // Macros for shooting distance.
            
            put(
                MacroOperation.ShootClose,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    () -> SequentialTask.Sequence(
                        new ShooterLowerKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                        new ShooterKickTask(TuningConstants.SHOOTER_FIRE_DURATION)),
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
                MacroOperation.ShootMiddle,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
                    () -> SequentialTask.Sequence(
                        new ShooterLowerKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION),
                        new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                        new ShooterKickTask(TuningConstants.SHOOTER_FIRE_DURATION)),
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
                MacroOperation.ShootFar,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    () -> SequentialTask.Sequence(
                        new ShooterLowerKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION),
                        new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION),
                        new ShooterKickTask(TuningConstants.SHOOTER_FIRE_DURATION)),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterLowerKicker,
                        Operation.ShooterExtendHood,
                        Operation.IntakeRotatingIn,
                        Operation.IntakeRotatingOut,
                    }));

            // Macros for the climbing arm.
            put(
                MacroOperation.ClimbingArmRetract,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_6,
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
            throw new RuntimeException("not a digital operation!");
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
            throw new RuntimeException("not an analog operation!");
        }

        AnalogOperationState analogState = (AnalogOperationState)state;
        return analogState.getState();
    }
}
