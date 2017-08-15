package practice.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.FrontDeskStaffEntity;
import practice.repository.FrontDeskStaffRepository;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ehunzeker on 6/5/17.
 */
@Controller
public class FrontDeskStaffController {
    @Autowired
    FrontDeskStaffRepository frontDeskStaffRepository;

    @RequestMapping(value = "/frontdeskstaff")
    public String frontDeskStaff(Model model) {
        createList(model);

        return "frontdeskstaff";
    }

    @RequestMapping(value = "/frontdeskstaff/find")
    public String frontDeskStaffFind(@RequestParam(value = "id") Integer id, Model model) {

        model.addAttribute("frontdeskstaffs", frontDeskStaffRepository.findOne(id));

        return "frontdeskstaff";
    }

    @RequestMapping(value = "/frontdeskstaff/delete")
    public String frontDeskStaffDelete(@RequestParam(value = "id") Integer id, Model model) {
        frontDeskStaffRepository.delete(id);

        createList(model);

        return "frontdeskstaff";
    }

    @RequestMapping(value = "/frontdeskstaff/add")
    public String frontDeskStaff(@RequestParam(value = "firstname") String firstname, @RequestParam(value = "lastname") String lastname, Model model) {
        FrontDeskStaffEntity frontDeskStaffEntity = new FrontDeskStaffEntity();
        frontDeskStaffEntity.setFirstName(firstname);
        frontDeskStaffEntity.setLastName(lastname);
        model.addAttribute("frontdeskstaffs", frontDeskStaffRepository.save(frontDeskStaffEntity));

        createList(model);

        return "frontdeskstaff";
    }

    private void createList(Model model) {
        ArrayList<FrontDeskStaffEntity> frontDeskStaffEntityArrayList = new ArrayList<>();
        Iterator<FrontDeskStaffEntity> frontDeskStaffEntityIterator = frontDeskStaffRepository.findAll().iterator();
        while (frontDeskStaffEntityIterator.hasNext()) {
            frontDeskStaffEntityArrayList.add(frontDeskStaffEntityIterator.next());
        }

        model.addAttribute("frontdeskstaffs", frontDeskStaffEntityArrayList);
    }
}

