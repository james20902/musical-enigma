package frc.robot.subsystems.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;

import java.util.function.DoubleSupplier;

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

public class QuadTurret extends SubsystemBase {
  /** Creates a new Intake. */
  private final TurretIO io;
  private final TurretIO io2;
  private final TurretIO io3;
  private final TurretIO io4;

  private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();
  private final TurretIOInputsAutoLogged inputs2 = new TurretIOInputsAutoLogged();
  private final TurretIOInputsAutoLogged inputs3 = new TurretIOInputsAutoLogged();
  private final TurretIOInputsAutoLogged inputs4 = new TurretIOInputsAutoLogged();


  PIDController posController = new PIDController(0.8, 0., .06);
  PIDController posController2 = new PIDController(0.8, 0., .06);
  PIDController posController3 = new PIDController(0.8, 0., .06);
  PIDController posController4 = new PIDController(0.8, 0., .06);


  Mechanism2d viz = new Mechanism2d(5.0, 5.0);
  MechanismRoot2d[] root = new MechanismRoot2d[4];
  MechanismLigament2d[] tVizController = new MechanismLigament2d[4];
  MechanismLigament2d[] tTargetController = new MechanismLigament2d[4];

  public QuadTurret(TurretIO io, TurretIO io2, TurretIO io3, TurretIO io4) {
    this.io = io;
    this.io2 = io2;
    this.io3 = io3;
    this.io4 = io4;

    posController.enableContinuousInput(0, 6.28);
    posController2.enableContinuousInput(0, 6.28);
    posController3.enableContinuousInput(0, 6.28);
    posController4.enableContinuousInput(0, 6.28);

    for (int i = 0; i < root.length; i++) {
      double solveX = (i % 2 == 0) ? 1.25 : 3.75;
      double solveY = (i < 2) ? 1.25 : 3.75;

      // 0 (1.25, 1.25) 1 (3.75, 1.25) 2 (1.25, 3.75) 3 (3.75, 3.75)
      root[i] = viz.getRoot("c"+i, solveX, solveY); 
      tVizController[i] = root[i].append(new MechanismLigament2d("torret"+i, 
                                                                            0.3, 
                                                                            0, 
                                                                            6, 
                                                                            new Color8Bit(Color.kPurple)));
      tTargetController[i] = root[i].append(new MechanismLigament2d("torretTargetuwu", 
                                                                            0.17, 
                                                                            0, 
                                                                            3, 
                                                                            new Color8Bit(Color.kRed)));
    }

    posController.setSetpoint(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    io2.updateInputs(inputs2);
    io3.updateInputs(inputs3);
    io4.updateInputs(inputs4);
    
    // update visual
    tTargetController[0].setAngle(Degrees.convertFrom(posController.getSetpoint(), Radians));
    tVizController[0].setAngle(Degrees.convertFrom(inputs.turretPositionRad, Radians));
    tTargetController[1].setAngle(Degrees.convertFrom(posController2.getSetpoint(), Radians));
    tVizController[1].setAngle(Degrees.convertFrom(inputs2.turretPositionRad, Radians));
    tTargetController[2].setAngle(Degrees.convertFrom(posController3.getSetpoint(), Radians));
    tVizController[2].setAngle(Degrees.convertFrom(inputs3.turretPositionRad, Radians));
    tTargetController[3].setAngle(Degrees.convertFrom(posController4.getSetpoint(), Radians));
    tVizController[3].setAngle(Degrees.convertFrom(inputs4.turretPositionRad, Radians));
    SmartDashboard.putData("turret", viz);

    Logger.processInputs("Turret", inputs);

    io.setTurretVoltage(posController.calculate(io.getTurretAngle()));
    io2.setTurretVoltage(posController2.calculate(io2.getTurretAngle()));
    io3.setTurretVoltage(posController3.calculate(io3.getTurretAngle()));
    io4.setTurretVoltage(posController4.calculate(io4.getTurretAngle()));
  }

  @Override
  public void simulationPeriodic() {
    io.simUpdate();
    io2.simUpdate();
    io3.simUpdate();
    io4.simUpdate();
  }

  public Command setTurretSetpointRad(DoubleSupplier position) {
    return run(() -> {
      posController.setSetpoint(position.getAsDouble());
      posController2.setSetpoint(position.getAsDouble());
      posController3.setSetpoint(position.getAsDouble());
      posController4.setSetpoint(position.getAsDouble());
    });
  }
}