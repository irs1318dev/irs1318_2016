package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.common.Helpers;
import frc.robot.common.IMechanism;
import frc.robot.common.PIDHandler;
import frc.robot.common.robotprovider.DoubleSolenoidValue;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IDoubleSolenoid;
import frc.robot.common.robotprovider.IEncoder;
import frc.robot.common.robotprovider.IMotor;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.common.robotprovider.ISolenoid;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class ShooterMechanism implements IMechanism
{
    private final IDashboardLogger logger;
    private final ITimer timer;

    private final IDoubleSolenoid kicker;
    private final IDoubleSolenoid hood;
    private final IMotor talon;
    private final IEncoder counter;
    private final ISolenoid readyLight;

    private final PowerManager powerManager;

    private Driver driver;
    private PIDHandler PID;

    private double counterRate;
    private int counterTicks;

    @Inject
    public ShooterMechanism(
        IDashboardLogger logger,
        IRobotProvider provider,
        ITimer timer,
        PowerManager powerManager) 
    {
        this.logger = logger;
        this.timer = timer;

        this.kicker = provider.getDoubleSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_A, ElectronicsConstants.SHOOTER_KICKER_CHANNEL_B);
        this.hood = provider.getDoubleSolenoid(ElectronicsConstants.SHOOTER_HOOD_CHANNEL_A, ElectronicsConstants.SHOOTER_HOOD_CHANNEL_B);
        this.talon = provider.getTalon(ElectronicsConstants.SHOOTER_TALON_CHANNEL);
        this.counter = provider.getEncoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
        this.readyLight = provider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.SHOOTER_READY_LIGHT_PORT);

        this.powerManager = powerManager;

        this.createPIDHandler();
    }

    public double getCounterRate()
    {
        return this.counterRate;
    }

    @Override
    public void readSensors()
    {
        // The actual velocity of the shooter wheel
        this.counterRate = this.counter.getRate();
        this.counterTicks = this.counter.get();
        this.logger.logNumber("shooter", "rate", this.counterRate);
        this.logger.logNumber("shooter", "ticks", this.counterTicks);
    }

    @Override
    public void update()
    {
        boolean spin = this.driver.getDigital(DigitalOperation.ShooterSpin);

        // The velocity set in the analog operation
        double velocityGoal = this.driver.getAnalog(AnalogOperation.ShooterSpeed);
        this.logger.logNumber("shooter", "velocityGoal", velocityGoal);

        double power = 0.0;
        boolean shouldLight = false;
        if (spin)
        {
            double speedPercentage = this.counterRate / TuningConstants.SHOOTER_MAX_COUNTER_RATE;
            shouldLight = velocityGoal != 0.0 && speedPercentage > velocityGoal - TuningConstants.SHOOTER_DEVIANCE && speedPercentage < velocityGoal + TuningConstants.SHOOTER_DEVIANCE;

            // Calculate the power required to reach the velocity goal     
            power = this.PID.calculateVelocity(velocityGoal, this.counterTicks);

            if (TuningConstants.SHOOTER_SCALE_BASED_ON_VOLTAGE)
            {
                power *= (TuningConstants.SHOOTER_VELOCITY_TUNING_VOLTAGE / this.powerManager.getBatteryVoltage());
                power = Helpers.EnforceRange(power, -TuningConstants.SHOOTER_MAX_POWER_LEVEL, TuningConstants.SHOOTER_MAX_POWER_LEVEL);
            }
        }

        this.readyLight.set(shouldLight);

        // Set the motor power with the calculated value
        this.talon.set(power);
        this.logger.logNumber("shooter", "power", power);

        // lower the kicker whenever we are rotating in or out, or when we are performing a shot macro
        boolean lowerKicker = this.driver.getDigital(DigitalOperation.ShooterLowerKicker)
            || this.driver.getDigital(DigitalOperation.IntakeRotatingIn)
            || this.driver.getDigital(DigitalOperation.IntakeRotatingOut);

        // control the kicker:
        this.kicker.set(lowerKicker ? DoubleSolenoidValue.Reverse : DoubleSolenoidValue.Forward);

        boolean extendHood = this.driver.getDigital(DigitalOperation.ShooterExtendHood);
        this.hood.set(extendHood ? DoubleSolenoidValue.Forward : DoubleSolenoidValue.Reverse);
    }

    @Override
    public void stop()
    {
        this.kicker.set(DoubleSolenoidValue.Off);
        this.hood.set(DoubleSolenoidValue.Off);
        this.talon.set(0.0);
        this.readyLight.set(false);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public void createPIDHandler() 
    {
        this.PID = new PIDHandler(
            TuningConstants.SHOOTER_VELOCITY_PID_KP_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KI_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KD_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KF_DEFAULT,
            TuningConstants.SHOOTER_VELOCITY_PID_KS_DEFAULT,
            -TuningConstants.SHOOTER_MAX_POWER_LEVEL, 
            TuningConstants.SHOOTER_MAX_POWER_LEVEL,
            this.timer);
    }
}
