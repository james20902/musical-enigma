package frc.robot.subsystems.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
  /** Creates a new Intake. */
  private final TurretIO io;

  private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();

  PIDController posController = new PIDController(1.0, 0., 0.);

  Mechanism2d viz = new Mechanism2d(5.0, 5.0);
  MechanismRoot2d root = viz.getRoot("core", 2.5, 2.5);
  MechanismLigament2d tVizController = root.append(new MechanismLigament2d("torret", 
                                                                            1.0, 
                                                                            0, 
                                                                            6, 
                                                                            new Color8Bit(Color.kPurple)));
  MechanismLigament2d tTargetController = root.append(new MechanismLigament2d("torretTargetuwu", 
                                                                            0.7, 
                                                                            0, 
                                                                            3, 
                                                                            new Color8Bit(Color.kRed)));

  public Turret(TurretIO io) {
    this.io = io;
    posController.setSetpoint(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    io.updateInputs(inputs);
    // update visual
    tTargetController.setAngle(Degrees.convertFrom(posController.getSetpoint(), Radians));
    tVizController.setAngle(Degrees.convertFrom(inputs.turretPositionRad, Radians));
    SmartDashboard.putData("turret", viz);

    Logger.processInputs("Turret", inputs);

    io.setTurretVoltage(posController.calculate(io.getTurretAngle()));
  }

  @Override
  public void simulationPeriodic() {
    io.simUpdate();
  }

  public Command moveForward() {
    return runOnce(
        () -> {
          io.setTurretVoltage(5.0); // while it is in the box
        });
  }

  public Command moveBackward() {
    return runOnce(
        () -> {
          io.setTurretVoltage(-5.0); // while it is in the box
        });
  }

  public Command stopTurret() {
    return runOnce(() -> {io.setTurretVoltage(0);});
  }


  public Command setTurretSetpointRad(double position) {
    return runOnce(() -> {posController.setSetpoint(position);});
  }
}