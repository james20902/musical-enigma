package frc.robot.subsystems.intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class IntakeIOSim implements IntakeIO {


    SingleJointedArmSim sim = new SingleJointedArmSim(
                    DCMotor.getNEO(1) , 
                        3.0, 
                        0.2, 
                        0.2, 
                        -0.5, // min angle
                        1.0, 
                        true, 
                        0.4);
    private double simVolts = 0.0;

    public void simUpdate() {
        sim.update(0.02);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.intakeAppliedVolts = simVolts;
        inputs.intakePositionRad = sim.getAngleRads();
        inputs.intakeVelocityRadPerSec = sim.getVelocityRadPerSec();
    }

    @Override
    public void setIntakeSpeed(double volts) {
        simVolts = volts;
        sim.setInputVoltage(simVolts);
    }

    @Override
    public double getIntakePosRad() {
        return sim.getAngleRads();
    }
    
}
