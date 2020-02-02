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
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
/*        new ShiftDescription(
            Shift.Debug,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON),*/
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        new AnalogOperationDescription(
            AnalogOperation.PositionStartingAngle,
            UserInputDevice.None,
            AnalogAxis.NONE,
            false,
            0.0),

        // Operations for the drive train
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainMoveForward,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_Y,
            ElectronicsConstants.INVERT_Y_AXIS,
            TuningConstants.DRIVETRAIN_Y_DEAD_ZONE),
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainTurn,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        // Operations for the shooter
        new DigitalOperationDescription(
            DigitalOperation.ShooterSpin,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
            ButtonType.Simple),

        // Operations for the intake
        new DigitalOperationDescription(
            DigitalOperation.IntakeRotatingIn,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.IntakeRotatingOut,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.IntakeExtend,
            UserInputDevice.Driver,
            0,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.IntakeRetract,
            UserInputDevice.Driver,
            180,
            ButtonType.Click),

        // Operations for general stuff
        new DigitalOperationDescription(
            DigitalOperation.DisablePID,
            UserInputDevice.CoDriver,   
            UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.EnablePID,
            UserInputDevice.CoDriver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.CancelBreachMacro,
            UserInputDevice.CoDriver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_16,
            ButtonType.Click),

        // Stinger operations
        new DigitalOperationDescription(
            DigitalOperation.StingerIn,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.StingerOut,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
            ButtonType.Simple),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        // Brake mode macro
        new MacroOperationDescription(
            MacroOperation.PIDBrake,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
            ButtonType.Simple,
            () -> new PIDBrakeTask(),
            new IOperation[]
            {
                DigitalOperation.DriveTrainUsePositionalMode,
                DigitalOperation.DriveTrainUseBrakeMode,
                AnalogOperation.DriveTrainLeftPosition,
                AnalogOperation.DriveTrainRightPosition,
            }),

        // Macros for shooting distance.
        new MacroOperationDescription(
            MacroOperation.SpinClose,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
            new IOperation[]
            {
                DigitalOperation.ShooterSpin,
                AnalogOperation.ShooterSpeed,
                DigitalOperation.ShooterLowerKicker,
                DigitalOperation.ShooterExtendHood,
                DigitalOperation.IntakeRotatingIn,
                DigitalOperation.IntakeRotatingOut,
            }),
        new MacroOperationDescription(
            MacroOperation.SpinMiddle,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new ShooterSpinUpTask(false, TuningConstants.SHOOTER_MIDDLE_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
            new IOperation[]
            {
                DigitalOperation.ShooterSpin,
                AnalogOperation.ShooterSpeed,
                DigitalOperation.ShooterLowerKicker,
                DigitalOperation.ShooterExtendHood,
                DigitalOperation.IntakeRotatingIn,
                DigitalOperation.IntakeRotatingOut,
            }),
        new MacroOperationDescription(
            MacroOperation.SpinFar,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new IntakeExtendTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new ShooterKickerTask(TuningConstants.SHOOTER_LOWER_KICKER_DURATION, true),
                new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY, TuningConstants.SHOOTER_SPIN_UP_DURATION)),
            new IOperation[]
            {
                DigitalOperation.ShooterSpin,
                AnalogOperation.ShooterSpeed,
                DigitalOperation.ShooterLowerKicker,
                DigitalOperation.ShooterExtendHood,
                DigitalOperation.IntakeRotatingIn,
                DigitalOperation.IntakeRotatingOut,
                DigitalOperation.IntakeExtend,
                DigitalOperation.IntakeRetract,
            }),
        new MacroOperationDescription(
            MacroOperation.Shoot,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new ShooterKickerTask(TuningConstants.SHOOTER_FIRE_DURATION, false),
                new ShooterSpinDownTask(TuningConstants.SHOOTER_REVERSE_DURATION)),
            new IOperation[]
            {
                DigitalOperation.ShooterSpin,
                AnalogOperation.ShooterSpeed,
                DigitalOperation.ShooterLowerKicker,
                DigitalOperation.ShooterExtendHood,
                DigitalOperation.IntakeRotatingIn,
                DigitalOperation.IntakeRotatingOut,
                DigitalOperation.IntakeExtend,
                DigitalOperation.IntakeRetract,
            }),
            
        // Macros for the climber.
        /*
        new MacroOperationDescription(
            MacroOperation.ClimberScale,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
            ButtonType.Click,
            () -> SequentialTask.Sequence(
                new ClimberFireTask(TuningConstants.CLIMBER_FIRE_DURATION),
                new ClimberClimbTask(TuningConstants.CLIMBER_CLIMB_DURATION, TuningConstants.CLIMBER_CLIMB_DISTANCE)),
            new IOperation[]
                {
                    DigitalOperation.ClimberWinchExtend,
                    AnalogOperation.ClimberWinchSpeed,
                    DigitalOperation.ClimberFiringPinExtend,
                }),
        new MacroOperationDescription(
            MacroOperation.ClimbingArmDeploy,
            UserInputDevice.CoDriver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_7,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new ClimbingArmShoulderTask(true),
                new ClimbingArmElbowTask(true)),
            new IOperation[]
            {
                DigitalOperation.ClimbingArmElbowUp,
                DigitalOperation.ClimbingArmElbowDown,
                DigitalOperation.ClimbingArmShoulderUp,
                DigitalOperation.ClimbingArmShoulderDown,
            }),
        new MacroOperationDescription(
            MacroOperation.ClimbingArmLifterUp,
            UserInputDevice.CoDriver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_8,
            ButtonType.Toggle,
            () -> new ClimbingArmLifterMoveTask(true),
            new IOperation[]
            {
                DigitalOperation.ClimbingArmExtend,
                DigitalOperation.ClimbingArmRetract,
            }),
        new MacroOperationDescription(
            MacroOperation.ClimbingArmLifterDown,
            UserInputDevice.CoDriver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_9,
            ButtonType.Toggle,
            () -> new ClimbingArmLifterMoveTask(false),
            new IOperation[]
            {
                DigitalOperation.ClimbingArmExtend,
                DigitalOperation.ClimbingArmRetract,
            }),*/
    };

    @Override
    public ShiftDescription[] getShiftSchema()
    {
        return ButtonMap.ShiftButtonSchema;
    }

    @Override
    public AnalogOperationDescription[] getAnalogOperationSchema()
    {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public DigitalOperationDescription[] getDigitalOperationSchema()
    {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public MacroOperationDescription[] getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
