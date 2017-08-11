package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import practice.model.ScheduleEntity;
import practice.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path="/schedule")
public class RestScheduleController
{
    @Autowired
    private ScheduleRepository scheduleRepository;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleEntity> addNewSchedule (ScheduleEntity scheduleEntity)
    {
        ScheduleEntity s = scheduleRepository.save(scheduleEntity);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }


    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScheduleEntity>> getAllUsers()
    {
        Iterable<ScheduleEntity> s = scheduleRepository.findAll();
        List<ScheduleEntity> scheduleEntityList = new ArrayList<>();
        s.iterator().forEachRemaining(scheduleEntityList::add);
        return new ResponseEntity<>(scheduleEntityList, HttpStatus.OK);

    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleEntity> deleteSchedule(@RequestParam("id") Integer id)
    {
        if(!scheduleRepository.exists(id))
            return null;
        else
        {
            scheduleRepository.delete(id);
            return new ResponseEntity<>(scheduleRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleEntity> getScheduleById(@RequestParam("id") Integer id)
    {

        ScheduleEntity c = scheduleRepository.findOne(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }
}
