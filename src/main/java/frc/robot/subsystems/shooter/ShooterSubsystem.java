package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.generic.GenericIOInputsAutoLogged;


// this is the high level hardware manager
// in other words, if you plan on doing some complex math
// or you would like to orchestrate and control other subsystems via commands
// you'll define said commands and multi-subsystem functions here

public class ShooterSubsystem extends SubsystemBase {

  // contains flywheel
  // contains hood

  private final ShooterIO io;

  private final GenericIOInputsAutoLogged inputs = new GenericIOInputsAutoLogged();

  public ShooterSubsystem(ShooterIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Generic", inputs);
  }

  public Command genericCommand() {
    return runOnce(io::genericFunction);
  }

}