package frc.robot.subsystems.shooter;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

// this is where implementation code goes
// in other words, for every set of hardware/simulation, make the code
// that actually does what you want the entire subsystem to do
public class ShooterIOSim implements ShooterIO {

    // pistons
    private final DoubleSolenoid m_doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    private final DoubleSolenoid m_doubleSolenoid2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    private boolean isHigh = false;

    // flywheel sim
    private final FlywheelSim sim = new FlywheelSim( 
        LinearSystemId.createFlywheelSystem(DCMotor.getBanebotsRs775(2), .001, .25),
         DCMotor.getBanebotsRs775(2)
    );

    public ShooterIOSim() {}

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.isHigh = isHigh;
        inputs.voltageSet = sim.getInputVoltage();
        inputs.currentFlywheelSpeedRadPerSecond = sim.getAngularVelocityRadPerSec();
        sim.update(.02);
    }

    // set piston state
    public void setHoodAngle(boolean isHigh) {
        this.isHigh = isHigh;
        if (isHigh) {
            m_doubleSolenoid.set(DoubleSolenoid.Value.kForward);
            m_doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
        } else {
            m_doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
            m_doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
        }
    }

    // set flywheel speed
    public void setFlywheelSpeed(double voltage) {
        sim.setInputVoltage(voltage);
    }
    
}
