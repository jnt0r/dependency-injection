package demo;

import de.jnt0r.initializer.annotations.Autowire;
import de.jnt0r.initializer.annotations.Component;

@Component
public class DemoController {

    @Autowire
    private DemoService demoService;

    @Autowire
    private DemoApplication demoApplication;

    public void sayHello() {
        demoApplication.toString();
        System.out.println(demoService.getMessage());
    }

}
