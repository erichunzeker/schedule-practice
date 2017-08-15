package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.ContactEntity;
import practice.repository.ContactRepository;
import practice.repository.PatientRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PatientRepository patientRepository;

    @RequestMapping(value = "/contact")
    public String contact(Model model) {
        createList(model);

        return "contact";
    }

    @RequestMapping(value = "/contact/find")
    public String contactFind(@RequestParam(value = "id") Integer id, Model model) {

        model.addAttribute("contacts", contactRepository.findOne(id));


        return "contact";
    }

    @RequestMapping(value = "/contact/delete")
    public String contactDelete(@RequestParam(value = "id") Integer id, Model model) {
        contactRepository.delete(id);

        createList(model);

        return "contact";
    }

    @RequestMapping(value = "/contact/add")
    public String contact(@RequestParam(value = "name") String name, @RequestParam(value = "id") Integer id, Model model) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactName(name);
        contactEntity.setPatientByPatId(patientRepository.findOne(id));
        model.addAttribute("contacts", contactRepository.save(contactEntity));

        createList(model);

        return "contact";
    }

    private void createList(Model model) {
        ArrayList<ContactEntity> contactList = new ArrayList<>();
        Iterator<ContactEntity> contactEntities = contactRepository.findAll().iterator();
        while (contactEntities.hasNext()) {
            contactList.add(contactEntities.next());
        }
        model.addAttribute("contacts", contactList);
    }
}
