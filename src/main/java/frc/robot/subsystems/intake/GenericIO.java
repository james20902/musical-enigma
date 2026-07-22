package frc.robot.subsystems.generic;

import org.littletonrobotics.junction.AutoLog;

// this is where all required functionality and data reporting should be defined
// in other words, anything that both your real and sim robot should be able to do
// needs to go in here!

public interface GenericIO {
    
    @AutoLog
    public class GenericIOInputs {
        public double field = 0.0;
    }

    public void updateInputs(GenericIOInputs inputs);

    public void genericFunction();

}
