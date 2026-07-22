package frc.robot.subsystems.generic;


// this is where implementation code goes
// in other words, for every set of hardware/simulation, make the code
// that actually does what you want the entire subsystem to do
public class GenericIOImplementation implements GenericIO {

    @Override
    public void updateInputs(GenericIOInputs inputs) {

    }

    @Override
    public void genericFunction() {
        System.out.println("I behave differently!");
    }
    
}
