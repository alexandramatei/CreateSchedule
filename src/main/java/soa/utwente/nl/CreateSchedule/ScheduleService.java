package soa.utwente.nl.CreateSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ScheduleService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    public static List<Integer> assignedTAs  = new ArrayList<Integer>();


    public boolean CreateSchedule(Integer sessionId){
        String sessionNrUrl="http://localhost:8082/session/RequiredTAs/" + sessionId;
        String availableUrl="http://localhost:8083/availability/available/" + sessionId;
        String maybeUrl="http://localhost:8083/availability/maybeAvailable/" + sessionId;
        String updateUrl="http://localhost:8082/updateSession/" + sessionId;


        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        int number = restTemplate.getForObject(sessionNrUrl, Integer.class);
        List<Integer> available = restTemplate.getForObject(availableUrl, List.class);
        List<Integer> maybe = restTemplate.getForObject(maybeUrl, List.class);


//      if there are enough TAs available
        if(available.size() > number){
            Collections.shuffle(available);
            assignedTAs = available.subList(0, number);

        }
        else if (available.size() + maybe.size() > number){
            if(available.size()>0)
                assignedTAs.addAll(available);
            Collections.shuffle(maybe);
            assignedTAs.addAll(maybe.subList(0, number-available.size()));
        }
        else{
            System.out.println("There are not enough TAs for this session");
            assignedTAs.addAll(available);
            assignedTAs.addAll(maybe);
        }

        System.out.println(updateUrl);
        return restTemplate.patchForObject(updateUrl, assignedTAs, boolean.class);

    }
}
