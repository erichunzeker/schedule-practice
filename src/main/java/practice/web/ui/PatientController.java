package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.ContactEntity;
import practice.model.DoctorEntity;
import practice.model.PatientEntity;
import practice.repository.ContactRepository;
import practice.repository.DoctorRepository;
import practice.repository.PatientRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class PatientController
{
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;


    @RequestMapping(value = "/patient")
    public String patient(Model model)
    {
        createList(model);

        return "patient";
    }

    @RequestMapping(value = "/patient/find")
    public String patientFind(@RequestParam(value="id")Integer id, Model model)
    {
        model.addAttribute("patients", patientRepository.findOne(id));
        return "patient";
    }

    @RequestMapping(value = "/patient/delete")
    public String patientDelete(@RequestParam(value="id")Integer id, Model model)
    {
        patientRepository.delete(id);

        createList(model);

        return "patient";
    }

    @RequestMapping(value = "/patient/add")
    public String patient(@RequestParam(value="name")String name, @RequestParam(value="docid")Integer docid, Model model)
    {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName(name);
        patientEntity.setDoctorByDocId(doctorRepository.findOne(docid));
        patientRepository.save(patientEntity);

        createList(model);

        return "patient";
    }

    private void createList(Model model)
    {
        ArrayList<PatientEntity> patientList = new ArrayList<>();
        Iterator<PatientEntity> patientEntities = patientRepository.findAll().iterator();
        while(patientEntities.hasNext()){
            patientList.add(patientEntities.next());
        }

        model.addAttribute("patients", patientList);

    }
}
