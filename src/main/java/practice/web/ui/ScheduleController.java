package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.ScheduleEntity;
import practice.repository.DoctorRepository;
import practice.repository.FrontDeskStaffRepository;
import practice.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class ScheduleController
{
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    FrontDeskStaffRepository frontDeskStaffRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @RequestMapping(value = "/schedule")
    public String schedule(Model model)
    {
        createList(model);

        return "schedule";
    }

    @RequestMapping(value = "/schedule/find")
    public String scheduleFind(@RequestParam(value="id")Integer id, Model model)
    {

        model.addAttribute("schedules", scheduleRepository.findOne(id));

        return "schedule";
    }

    @RequestMapping(value = "/schedule/delete")
    public String scheduleDelete(@RequestParam(value="id")Integer id, Model model)
    {
        scheduleRepository.delete(id);

        createList(model);

        return "schedule";
    }

    @RequestMapping(value = "/schedule/add")
    public String schedule(@RequestParam(value="id")Integer id, @RequestParam(value="docid")Integer docid, Model model)
    {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setFrontDeskStaffByFrontDeskStaffId(frontDeskStaffRepository.findOne(id));
        scheduleEntity.setDoctorByDocId(doctorRepository.findOne(docid));
        model.addAttribute("schedules", scheduleRepository.save(scheduleEntity));

        createList(model);

        return "schedule";
    }

    private void createList(Model model)
    {
        ArrayList<ScheduleEntity> scheduleEntities = new ArrayList<>();
        Iterator<ScheduleEntity> scheduleEntityIterator = scheduleRepository.findAll().iterator();
        while(scheduleEntityIterator.hasNext()){
            scheduleEntities.add(scheduleEntityIterator.next());
        }

        model.addAttribute("schedules", scheduleEntities);
    }
}
