package soa.utwente.nl.CreateSchedule.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import soa.utwente.nl.CreateSchedule.ScheduleService;


@RestController
public class ScheduleRestController {
    @Autowired private ScheduleService service;

        @GetMapping(value="/createSchedule/{id}")
        public boolean createSchedule(@PathVariable Integer id){
            System.out.println("Call");
            return service.CreateSchedule(id);}

}
