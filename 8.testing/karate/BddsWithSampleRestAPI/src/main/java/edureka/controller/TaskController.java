package edureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @GetMapping("/showMyInfo")
    public Map displayWelcomeMessage(){
        Map<String,String> hm =new HashMap<>();
        hm.put("city","hyderabad");
        hm.put("name","Manideep kumar");
        hm.put("State","Andhra pradesh");
        hm.put("dream_job","Oracle");
        return hm;
    }
    @GetMapping("/showMyHobbies")
    public Map showMyHobbies (){
        Map<String,String> hm =new HashMap<>();
        hm.put("hobbies1","yoga");
        hm.put("hobbei2","shuttle");
        return hm;
    }
    @GetMapping("/")
    public String taskDemo(){
        return "Task demonstration";
    }
}
