package frc.robot.driver;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import frc.robot.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;
import frc.robot.driver.common.buttons.*;
import frc.robot.driver.common.descriptions.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    @SuppressWarnings("serial")
    private static Map<Shift, ShiftDescription> ShiftButtons = new HashMap<Shift, ShiftDescription>()
    {
        {
/*
            put(
                Shift.Debug,
                new ShiftDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON));
*/
        }
    };

    @SuppressWarnings("serial")
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            put(
                Operation.PositionStartingAngle,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.NONE,
                    false,
                    0.0));

            // Operations for the drive train
            put(
                Operation.DriveTrainMoveForward,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_Y,
                    ElectronicsConstants.INVERT_Y_AXIS,
                    TuningConstants.DRIVETRAIN_Y_DEAD_ZONE));
            put(
                Operation.DriveTrainTurn,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DRIVETRAIN_X_DEAD_ZONE));
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
                Operation.DriveTrainUseBrakeMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainLeftPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.NONE,
                    false,
                    0.0));
            put(
                Operation.DriveTrainRightPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.NONE,
                    false,
                    0.0));
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
                    AnalogAxis.NONE));
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

            // Operations for the climber
            put(
                Operation.ClimberWinchExtend,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimberWinchSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.NONE));
            put(
                Operation.ClimberFiringPinExtend,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimberFiringPinRetract,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimberArmUp,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimberArmDown,
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
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.StingerOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
        }
    };

    @SuppressWarnings("serial")
    public static Map<MacroOperation, MacroOperationDescription> MacroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {
            // Brake mode macro
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
                        Operation.DriveTrainUseBrakeMode,
                        Operation.DriveTrainLeftPosition,
                        Operation.DriveTrainRightPosition,
                    }));

            // Macros for shooting distance.
            put(
                MacroOperation.SpinClose,
                new MacroOperationDescription(
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
            
            // Macros for the climber.
/*            put(
                MacroOperation.ClimberScale,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click,
                    () -> SequentialTask.Sequence(
                        new ClimberFireTask(TuningConstants.CLIMBER_FIRE_DURATION),
                        new ClimberClimbTask(TuningConstants.CLIMBER_CLIMB_DURATION, TuningConstants.CLIMBER_CLIMB_DISTANCE)),
                    new Operation[]
                        {
                            Operation.ClimberWinchExtend,
                            Operation.ClimberWinchSpeed,
                            Operation.ClimberFiringPinExtend,
                        })); */
            /*
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
                    */
        }
    };

    @Override
    public Map<Shift, ShiftDescription> getShiftMap()
    {
        return ButtonMap.ShiftButtons;
    }

    @Override
    public Map<Operation, OperationDescription> getOperationSchema()
    {
        return ButtonMap.OperationSchema;
    }

    @Override
    public Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
