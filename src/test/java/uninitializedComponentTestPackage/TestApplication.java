package uninitializedComponentTestPackage;

import de.jnt0r.initializer.Initializer;
import de.jnt0r.initializer.annotations.Autowire;

public class TestApplication {

    @Autowire
    private NotAComponent notAComponent;

    /** NOT USED IN TEST */
    public static void main(String[] args) {
        TestApplication application = Initializer.initialize(TestApplication.class, args);
    }

}
