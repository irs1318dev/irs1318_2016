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
import frc.robot.driver.Operation;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class StingerMechanism implements IMechanism
{
    private final IDashboardLogger logger;

    private final IMotor motor;

    private Driver driver;

    @Inject
    public StingerMechanism(
        IDashboardLogger logger,
        IRobotProvider provider)
    {
        this.logger = logger;
        this.motor = provider.getTalon(ElectronicsConstants.STINGER_MOTOR_CHANNEL);
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        double desiredVelocity;
        if (this.driver.getDigital(Operation.StingerOut))
        {
            desiredVelocity = TuningConstants.STINGER_MAX_VELOCTIY;
        }
        else if (this.driver.getDigital(Operation.StingerIn))
        {
            desiredVelocity = -TuningConstants.STINGER_MAX_VELOCTIY;
        }
        else
        {
            desiredVelocity = TuningConstants.STINGER_SLOW_BACK_VELOCTIY;
        }

        this.logger.logNumber("stinger", "velocity", desiredVelocity);
        this.motor.set(-desiredVelocity);
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
       this.driver = driver;
    }
}
