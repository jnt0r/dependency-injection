package testApplications;

import de.jnt0r.initializer.Initializer;
import de.jnt0r.initializer.annotations.Autowire;
import testApplications.components.StringProviderComponent;

public class TestApplication {

    @Autowire
    private StringProviderComponent stringProviderComponent;

    public static void main(String[] args) {
        Initializer.initialize(TestApplication.class, args);
    }

    public String getComponentString() {
        return stringProviderComponent.getItWorks();
    }
}
