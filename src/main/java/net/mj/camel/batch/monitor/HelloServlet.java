package net.mj.camel.batch.monitor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServlet {

    @RequestMapping("/hello")
    public String hello() {
        return "hi";
    }
}
