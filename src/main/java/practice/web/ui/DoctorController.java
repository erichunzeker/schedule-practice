package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.DoctorEntity;
import practice.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class DoctorController {
    @Autowired
    DoctorRepository doctorRepository;

    @RequestMapping(value = "/doctor")
    public String doctor(Model model) {
        createList(model);

        return "doctor";
    }

    @RequestMapping(value = "/doctor/find")
    public String doctorFind(@RequestParam(value = "id") Integer id, Model model) {

        model.addAttribute("doctors", doctorRepository.findOne(id));

        return "doctor";
    }

    @RequestMapping(value = "/doctor/delete")
    public String doctorDelete(@RequestParam(value = "id") Integer id, Model model) {
        doctorRepository.delete(id);

        createList(model);

        return "doctor";
    }

    @RequestMapping(value = "/doctor/add")
    public String doctor(@RequestParam(value = "name") String name, Model model) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDocName(name);
        model.addAttribute("doctors", doctorRepository.save(doctorEntity));

        createList(model);

        return "doctor";
    }

    private void createList(Model model) {
        ArrayList<DoctorEntity> docList = new ArrayList<>();
        Iterator<DoctorEntity> doctorEntities = doctorRepository.findAll().iterator();
        while (doctorEntities.hasNext()) {
            docList.add(doctorEntities.next());
        }

        model.addAttribute("doctors", docList);
    }
}

