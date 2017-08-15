package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.FrontDeskStaffEntity;
import practice.repository.FrontDeskStaffRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path = "/frontdeskstaff")
public class RestFrontDeskStaffController {
    @Autowired
    private FrontDeskStaffRepository frontDeskStaffRepository;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FrontDeskStaffEntity> addNewFrontDeskStaff(FrontDeskStaffEntity frontDeskStaffEntity) {
        FrontDeskStaffEntity f = frontDeskStaffRepository.save(frontDeskStaffEntity);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FrontDeskStaffEntity>> getAllUsers() {

        Iterable<FrontDeskStaffEntity> f = frontDeskStaffRepository.findAll();
        List<FrontDeskStaffEntity> frontDeskStaffEntityList = new ArrayList<>();
        f.iterator().forEachRemaining(frontDeskStaffEntityList::add);
        return new ResponseEntity<>(frontDeskStaffEntityList, HttpStatus.OK);

    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FrontDeskStaffEntity> deleteFrontDeskStaff(@RequestParam("id") Integer id) {
        if (!frontDeskStaffRepository.exists(id))
            return null;
        else {
            frontDeskStaffRepository.delete(id);
            return new ResponseEntity<>(frontDeskStaffRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FrontDeskStaffEntity> getFrontDeskStaffById(@RequestParam("id") Integer id) {

        FrontDeskStaffEntity c = frontDeskStaffRepository.findOne(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }
}
