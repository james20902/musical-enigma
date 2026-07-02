package frc.robot.subsystems.turret;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class TurretIOSim implements TurretIO {


    DCMotorSim sim = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.005, 3.0),
        DCMotor.getNEO(1)
    );

    public TurretIOSim() {
        sim.setAngle(0);
    }

    public void simUpdate() {
        sim.update(0.02);
        sim.setAngle(getTurretAngle() % (2*Math.PI));
    }

    @Override
    public void updateInputs(TurretIOInputs inputs) {
        inputs.turretPositionRad = sim.getAngularPositionRad();
    }

    @Override
    public double getTurretAngle() {
        return sim.getAngularPositionRad();
    }

    @Override
    public void setTurretVoltage(double volts) {
        sim.setInputVoltage(volts);
    }
    
}
