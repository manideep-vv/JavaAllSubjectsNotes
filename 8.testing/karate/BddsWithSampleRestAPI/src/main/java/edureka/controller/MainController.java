package edureka.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
//@RestController
public class MainController {
    @PostConstruct
    public void m1(){
        System.out.println("my main controler have been instantiated, ready to accept incoming requests");
    }
    @GetMapping("/")
    public String displayWelcomeMessage(){
        return "Hello visitor!";
    }
}
