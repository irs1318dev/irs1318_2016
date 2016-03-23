package org.usfirst.frc.team1318.robot.Intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

public class IntakeController implements IController
{
    private final IntakeComponent intake;
    private Driver driver;

    public IntakeController(IntakeComponent intake) 
    {
        this.intake = intake;
    }

    @Override
    public void update()
    {
        // Check for "intake base" extend desire, and extend or retract appropriately
        if (this.driver.getDigital(Operation.IntakeExtend))
        {
            this.intake.extendOrRetract(true);
        }
        else if (this.driver.getDigital(Operation.IntakeRetract))
        {
            this.intake.extendOrRetract(false);
        }

        // Roll the intake in, out, or not at all when appropriate
        if (this.driver.getDigital(Operation.IntakeRotatingIn))
        {
            this.intake.setIntakeSpeed(TuningConstants.INTAKE_IN_POWER_LEVEL);
        }
        else if (this.driver.getDigital(Operation.IntakeRotatingOut))
        {
            this.intake.setIntakeSpeed(TuningConstants.INTAKE_OUT_POWER_LEVEL);
        }
        else 
        {
            this.intake.setIntakeSpeed(0.0);
        }

        // Turn on the intake light if the through beam sensor is broken
        this.intake.setIntakeLight(this.intake.getThroughBeamBroken());
    }

    @Override
    public void stop()
    {
        this.intake.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
