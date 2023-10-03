package edureka.controller;

import edureka.model.Worker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @PostConstruct
    public void m1(){
        System.out.println(WorkerController.class+"has been initialised");
    }
    @GetMapping("/showWorker")
    public Worker getWorkerDetails(){
        return new Worker(29,"manideep kumar",22000,"I.T.Analyst");
    }

    @GetMapping("/all/showWorkers")
    public List<Worker> getAllWorkerDetails(){
        List<Worker> workerList =new ArrayList<>();
        workerList.add(new Worker(29,"manideep kumar",22000,"I.T.Analyst"));
        workerList.add(new Worker(30,"sriram",33000,"Engineer microservices"));
        workerList.add(new Worker(40,"santhoshi",43000,"Associate"));
        return  workerList;
    }
}
