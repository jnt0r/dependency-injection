package demo;

import de.jnt0r.autowire.Initializer;
import de.jnt0r.autowire.annotations.Autowire;

public class DemoApplication {

    @Autowire
    private DemoController demoController;

    public static void main(String[] args) {
        DemoApplication application = Initializer.initialize(DemoApplication.class, args);
        application.sayWhat();
    }

    public void sayWhat() {
        demoController.sayHello();
    }

}
