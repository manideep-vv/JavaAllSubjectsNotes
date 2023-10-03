package edureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@RestController
public class HomeController {
    @PostConstruct
    public void m1(){
        System.out.println(HomeController.class+"has been initialised");
    }
    @GetMapping("/")
    public String displayWelcomeMessage(){
        return "Hello visitor! \n Visiting time: "+ LocalDateTime.now();
    }
}
