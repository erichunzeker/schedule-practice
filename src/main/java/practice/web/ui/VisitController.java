package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.VisitEntity;
import practice.repository.ScheduleRepository;
import practice.repository.VisitRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class VisitController {
    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @RequestMapping(value = "/visit")
    public String visit(Model model) {
        createList(model);

        return "visit";
    }

    @RequestMapping(value = "/visit/find")
    public String visitFind(@RequestParam(value = "id") Integer id, Model model) {

        model.addAttribute("visits", visitRepository.findOne(id));

        return "visit";
    }

    @RequestMapping(value = "/visit/delete")
    public String visitDelete(@RequestParam(value = "id") Integer id, Model model) {
        visitRepository.delete(id);

        createList(model);


        return "visit";
    }

    @RequestMapping(value = "/visit/add")
    public String visit(@RequestParam(value = "id") Integer id, Model model) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setScheduleByScheduleId(scheduleRepository.findOne(id));

        model.addAttribute("visits", visitRepository.save(visitEntity));

        createList(model);


        return "visit";
    }

    private void createList(Model model) {
        ArrayList<VisitEntity> visitEntityArrayList = new ArrayList<>();
        Iterator<VisitEntity> doctorEntities = visitRepository.findAll().iterator();
        while (doctorEntities.hasNext()) {
            visitEntityArrayList.add(doctorEntities.next());
        }

        model.addAttribute("visits", visitEntityArrayList);
    }
}

