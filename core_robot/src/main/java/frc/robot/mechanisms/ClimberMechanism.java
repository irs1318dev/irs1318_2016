package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.robotprovider.DoubleSolenoidValue;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IDoubleSolenoid;
import frc.robot.common.robotprovider.IMotor;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.driver.Operation;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class ClimberMechanism implements IMechanism
{
    private final IDashboardLogger logger;

    private IMotor winch;
    private IDoubleSolenoid firingPin;
    private IDoubleSolenoid arm;
    private boolean isArmExtended;

    private Driver driver;

    @Inject
    public ClimberMechanism(
        IDashboardLogger logger,
        IRobotProvider provider)
    {
        this.logger = logger;

        this.winch = provider.getTalon(ElectronicsConstants.CLIMBER_WINCH_MOTOR_CHANNEL);
        
        this.firingPin = provider.getDoubleSolenoid(
            ElectronicsConstants.PCM_A_MODULE, 
            ElectronicsConstants.CLIMBER_FIRING_PIN_CHANNEL_A, 
            ElectronicsConstants.CLIMBER_FIRING_PIN_CHANNEL_B);
        this.arm = provider.getDoubleSolenoid(
            ElectronicsConstants.PCM_A_MODULE,
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_A, 
            ElectronicsConstants.CLIMBER_ARM_SOLENOID_CHANNEL_B);

        this.isArmExtended = false;
    }

    public boolean getArmIsExtended()
    {
        return this.isArmExtended;
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {   
        // Extend or retract the arm as desired
        if (this.driver.getDigital(Operation.ClimberArmUp))
        {
            this.arm.set(DoubleSolenoidValue.Forward);
            this.isArmExtended = true;
        }
        else if (this.driver.getDigital(Operation.ClimberArmDown))
        {
            this.arm.set(DoubleSolenoidValue.Reverse);
            this.isArmExtended = false;
        }

        // Set the speed of the winch according to desire     
        double currentWinchSpeed = 0.0;
        if (this.driver.getDigital(Operation.ClimberWinchExtend))
        {
            currentWinchSpeed = this.driver.getAnalog(Operation.ClimberWinchSpeed);
        }

        this.winch.set(currentWinchSpeed);

        // Set the state of the hook according to desire
        if (this.driver.getDigital(Operation.ClimberFiringPinExtend))
        {
            this.logger.logBoolean("c", "extend", true);
            this.firingPin.set(DoubleSolenoidValue.Forward);
        } 
        else if (this.driver.getDigital(Operation.ClimberFiringPinRetract))
        {
            this.logger.logBoolean("c", "extend", false);
            this.firingPin.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop()
    {
        this.winch.set(0.0);
        this.firingPin.set(DoubleSolenoidValue.Off);
        this.arm.set(DoubleSolenoidValue.Off);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
