package demo;

import de.jnt0r.initializer.annotations.Component;

@Component("myDemoService")
public class DemoService {

    public String getMessage() {
        return "Hello from CustomerService";
    }

}
