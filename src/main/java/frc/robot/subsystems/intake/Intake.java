package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final IntakeIO io;

  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

  PIDController armControl = new PIDController(1.0, 0., 0.);

  Mechanism2d viz = new Mechanism2d(5.0, 5.0);

  public Intake(IntakeIO io) {
    this.io = io;
    armControl.setSetpoint(0.0);

    // viz.getRoot("apple", 2.5, 2.5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    io.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);
    // Logger.recordOutput("intake arm", viz);s

    // drive intake
    io.setIntakeSpeed(armControl.calculate(io.getIntakePosRad()) * 12.0);
    // System.out.println(armControl.calculate(io.getIntakePosRad()) * 12.0);
  }

  @Override
  public void simulationPeriodic() {
    io.simUpdate();
  }

  public Command moveForward() {
    return runEnd(
        () -> {
          io.setIntakeSpeed(5.0); // while it is in the box
        },
        () -> {
          io.setIntakeSpeed(0.0); // when it goes out of the box
        });
  }

  public Command moveBack() {
    return run(() -> {
      io.setIntakeSpeed(-5.0);
    });
  }

  public Command stopIntake() {
    return runOnce(() -> io.setIntakeSpeed(0.0));
  }

  public void setIntakeSetpoint(double position) {
    armControl.setSetpoint(position);
    
  }
}