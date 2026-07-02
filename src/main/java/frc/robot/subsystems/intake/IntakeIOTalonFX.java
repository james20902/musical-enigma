package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

public class IntakeIOTalonFX implements IntakeIO {

    TalonFX talon;
    VoltageOut request;

    public IntakeIOTalonFX() {
        talon = new TalonFX(67);
        request = new VoltageOut(0.0);
    }

    @Override
    public void simUpdate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'simUpdate'");
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.intakePositionRad = talon.getPosition().getValue().in(Radians);
        inputs.intakeVelocityRadPerSec = talon.getVelocity().getValue().in(RadiansPerSecond);
        inputs.intakeAppliedVolts = talon.getMotorVoltage().getValueAsDouble();
        inputs.intakeCurrentAmps = talon.getSupplyCurrent().getValue().in(Amps);
    }

    @Override
    public void setIntakeSpeed(double volts) {
        // this
        talon.setControl(new VoltageOut(volts));

        // or that
        request.Output = volts;
        talon.setControl(request);
    }

    @Override
    public double getIntakePosRad() {
        return talon.getPosition().getValue().in(Radians);
    }
    
}
