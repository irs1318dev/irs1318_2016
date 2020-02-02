package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ShooterSpinDownTask extends TimedTask implements IControlTask
{
    private double startingSpeed;

    public ShooterSpinDownTask(double spinDuration)
    {
        super(spinDuration);
        this.startingSpeed = 0.0;
    }

    @Override
    public void begin()
    {
        super.begin();

        this.startingSpeed = this.getAnalogOperationState(AnalogOperation.ShooterSpeed);

        this.setDigitalOperationState(DigitalOperation.ShooterSpin, true);
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(DigitalOperation.ShooterSpin, false);
        this.setAnalogOperationState(AnalogOperation.ShooterSpeed, 0.0);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(DigitalOperation.ShooterSpin, true);
        this.setAnalogOperationState(AnalogOperation.ShooterSpeed, (1.0 - this.getRatioComplete()) * this.startingSpeed);
        this.setDigitalOperationState(DigitalOperation.ShooterExtendHood, false);
    }

    @Override
    public void end()
    {
    }
}
