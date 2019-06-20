package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.robotprovider.DoubleSolenoidValue;
import frc.robot.common.robotprovider.IAnalogInput;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IDoubleSolenoid;
import frc.robot.common.robotprovider.IMotor;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.common.robotprovider.ISolenoid;
import frc.robot.driver.Operation;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class IntakeMechanism implements IMechanism
{
    private final IDashboardLogger logger;

    private final IMotor motor;
    private final IDoubleSolenoid intakeSolenoid;
    private final ISolenoid intakeLight;
    private final IAnalogInput throughBeamSensor;

    private Driver driver;

    private boolean isThroughBeamBroken;

    @Inject
    public IntakeMechanism(
        IDashboardLogger logger,
        IRobotProvider provider)
    {
        this.logger = logger;

        this.motor = provider.getTalon(ElectronicsConstants.INTAKE_MOTOR_CHANNEL);
        this.intakeSolenoid = provider.getDoubleSolenoid(
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_A,
            ElectronicsConstants.INTAKE_SOLENOID_CHANNEL_B);
        this.intakeLight = provider.getSolenoid(ElectronicsConstants.PCM_B_MODULE, ElectronicsConstants.INTAKE_LIGHT_CHANNEL);
        this.throughBeamSensor = provider.getAnalogInput(ElectronicsConstants.INTAKE_THROUGH_BEAM_SENSOR_CHANNEL);

        this.isThroughBeamBroken = false;
    }

    @Override
    public void update()
    {
        // Check for "intake base" extend desire, and extend or retract appropriately
        if (this.driver.getDigital(Operation.IntakeExtend))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(Operation.IntakeRetract))
        {
            this.intakeSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        // Roll the intake in, out, or not at all when appropriate
        if (this.driver.getDigital(Operation.IntakeRotatingIn))
        {
            this.motor.set(TuningConstants.INTAKE_IN_POWER_LEVEL);
        }
        else if (this.driver.getDigital(Operation.IntakeRotatingOut))
        {
            this.motor.set(TuningConstants.INTAKE_OUT_POWER_LEVEL);
        }
        else 
        {
            this.motor.set(0.0);
        }

        // Turn on the intake light if the through beam sensor is broken
        this.intakeLight.set(this.isThroughBeamBroken);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
        this.intakeSolenoid.set(DoubleSolenoidValue.Off);
        this.intakeLight.set(false);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    @Override
    public void readSensors()
    {
        this.isThroughBeamBroken = (this.throughBeamSensor.getVoltage() < 2.5);
        this.logger.logBoolean("Intake", "Through-beam", this.isThroughBeamBroken);
    }
}
