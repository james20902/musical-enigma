package frc.robot.subsystems.turret;

import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {
    
    @AutoLog
    public class TurretIOInputs {
        public double turretPositionRad = 0.0;
        public double turretTargetRad = 0.0;
    }

    public void simUpdate();

    public void updateInputs(TurretIOInputs inputs);

    public double getTurretAngle();

    public void setTurretVoltage(double volts);


}
