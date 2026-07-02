package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    
    @AutoLog
    public class IntakeIOInputs {
        public double intakePositionRad = 0.0;
        public double intakeVelocityRadPerSec = 0.0;
        public double intakeAppliedVolts = 0.0;
        public double intakeCurrentAmps = 0.0;
    }

    public void simUpdate();

    public void updateInputs(IntakeIOInputs inputs);

    public void setIntakeSpeed(double volts);
    //sparkmax spark.setSpeed(12);
    //talonfx talon.set(12)

    public double getIntakePosRad();

}
