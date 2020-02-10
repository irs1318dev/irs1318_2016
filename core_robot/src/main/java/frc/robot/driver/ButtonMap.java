package frc.robot.driver;

import javax.inject.Singleton;

import frc.robot.*;
import frc.robot.driver.common.*;
import frc.robot.driver.common.buttons.*;
import frc.robot.driver.common.descriptions.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
        new ShiftDescription(
            Shift.DriverDebug,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        // Operations for the drive train
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainMoveForward,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSY,
            ElectronicsConstants.INVERT_Y_AXIS,
            TuningConstants.DRIVETRAIN_Y_DEAD_ZONE),
        new AnalogOperationDescription(
            AnalogOperation.DriveTrainTurn,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RSX,
            ElectronicsConstants.INVERT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        // Operations for the shooter
        // new DigitalOperationDescription(
        //     DigitalOperation.ShooterSpin,
        //     UserInputDevice.Driver,
        //     UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
        //     ButtonType.Simple),

        // Operations for the intake
        new DigitalOperationDescription(
            DigitalOperation.IntakeRotatingIn,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.IntakeRotatingOut,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.DriverDebug,
            Shift.None,
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
            UserInputDevice.Operator,   
            UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.EnablePID,
            UserInputDevice.Operator,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.CancelBreachMacro,
            UserInputDevice.Operator,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_16,
            ButtonType.Click),

        // Stinger operations
        new DigitalOperationDescription(
            DigitalOperation.StingerIn,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_X_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Simple),
        new DigitalOperationDescription(
            DigitalOperation.StingerOut,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.DriverDebug,
            Shift.None,
            ButtonType.Simple),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        // Brake mode macro
        new MacroOperationDescription(
            MacroOperation.PIDBrake,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LT,
            0.5,
            1.0,
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
            true,
            MacroOperation.SpinClose,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_X_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
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
            true,
            MacroOperation.SpinMiddle,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
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
            true,
            MacroOperation.SpinFar,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            Shift.DriverDebug,
            Shift.DriverDebug,
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
            AnalogAxis.XBONE_RT,
            0.5,
            1.0,
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
            UserInputDevice.Operator,
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
            UserInputDevice.Operator,
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
            UserInputDevice.Operator,
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
