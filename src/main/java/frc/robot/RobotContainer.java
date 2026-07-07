// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.turret.QuadTurret;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretIOSim;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Intake intake;
  private final QuadTurret turret;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    turret = new QuadTurret(new TurretIOSim(), new TurretIOSim(), new TurretIOSim(), new TurretIOSim());
    switch (Constants.currentMode) {
      case REAL:
        intake = new Intake(new IntakeIOSim());
        break;

      case SIM:
        

        intake = new Intake(new IntakeIOSim());
        break;

      default:
        // Replayed robot, disable IO implementations
        intake = new Intake(new IntakeIO() {
            @Override
            public void updateInputs(IntakeIOInputs inputs) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'updateInputs'");
            }

            @Override
            public void setIntakeSpeed(double volts) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setIntakeSpeed'");
            }

            @Override
            public double getIntakePosRad() {
              // TODO Auto-generated method stub
              throw new UnsupportedOperationException("Unimplemented method 'getIntakePosRad'");
            }

            @Override
            public void simUpdate() {
              // TODO Auto-generated method stub
              throw new UnsupportedOperationException("Unimplemented method 'simUpdate'");
            }});
        break;
    }

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    
    turret.setDefaultCommand(
      new SequentialCommandGroup(
        turret.setTurretSetpointRad(() ->
          -Math.atan2(MathUtil.applyDeadband(controller.getLeftY(), 0.2),
           MathUtil.applyDeadband(controller.getLeftX(), 0.2))
        )
      )
    );
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new PrintCommand("");
  }
}
