package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

// this is where all required functionality and data reporting should be defined
// in other words, anything that both your real and sim robot should be able to do
// needs to go in here!

public interface ShooterIO {
    
    @AutoLog
    public class ShooterIOInputs {
        public boolean isHigh = false;
        public double currentFlywheelSpeedRadPerSecond = 0.0;
        public double voltageSet = 0.0;
    }

    public void updateInputs(ShooterIOInputs inputs);

    // set piston state
    public void setHoodAngle(boolean isHigh);
    // set flywheel speed
    public void setFlywheelSpeed(double voltage);

}
