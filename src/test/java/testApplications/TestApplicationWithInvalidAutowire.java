package testApplications;

import de.jnt0r.initializer.Initializer;
import de.jnt0r.initializer.annotations.Autowire;
import testApplications.components.NotAComponent;

public class TestApplicationWithInvalidAutowire {

    @Autowire
    private NotAComponent notAComponent;

    /** NOT USED IN TEST */
    public static void main(String[] args) {
        Initializer.initialize(TestApplicationWithInvalidAutowire.class, args);
    }

}
