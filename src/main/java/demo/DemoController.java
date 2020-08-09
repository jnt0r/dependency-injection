package demo;

import de.jnt0r.autowire.annotations.Autowire;
import de.jnt0r.autowire.annotations.Component;

@Component
public class DemoController {

    @Autowire
    private DemoService demoService;

    public void sayHello() {
        System.out.println(demoService.getMessage());
    }

}
