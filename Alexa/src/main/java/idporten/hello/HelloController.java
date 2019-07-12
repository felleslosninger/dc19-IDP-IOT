package idporten.hello;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String getHellos() {
        return "Hello from REST";
    }

    @RequestMapping("/hello/{id}")
    public String getHello(@PathVariable String id) {
        return "Hello specific";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hello")
    public void setHello() {
        System.out.println("Controller 2");
    }
}

