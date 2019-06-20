package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;
import frc.robot.driver.Operation;

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

        this.startingSpeed = this.getAnalogOperationState(Operation.ShooterSpeed);

        this.setDigitalOperationState(Operation.ShooterSpin, true);
    }

    @Override
    public void stop()
    {
        super.stop();
        this.setDigitalOperationState(Operation.ShooterSpin, false);
        this.setAnalogOperationState(Operation.ShooterSpeed, 0.0);
    }

    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.ShooterSpin, true);
        this.setAnalogOperationState(Operation.ShooterSpeed, (1.0 - this.getRatioComplete()) * this.startingSpeed);
        this.setDigitalOperationState(Operation.ShooterExtendHood, false);
    }

    @Override
    public void end()
    {
    }
}
