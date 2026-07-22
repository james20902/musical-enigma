package frc.robot.subsystems.generic;

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

public class GenericSubsystem extends SubsystemBase {
  private final GenericIO io;

  private final GenericIOInputsAutoLogged inputs = new GenericIOInputsAutoLogged();

  public GenericSubsystem(GenericIO io) {
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