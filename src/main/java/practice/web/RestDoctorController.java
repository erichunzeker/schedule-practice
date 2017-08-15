package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.DoctorEntity;
import practice.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path = "/doctor")
public class RestDoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorEntity> addNewDoctor(DoctorEntity doctorEntity) {
        DoctorEntity d = doctorRepository.save(doctorEntity);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DoctorEntity>> getAllUsers() {

        Iterable<DoctorEntity> d = doctorRepository.findAll();
        List<DoctorEntity> doctorEntityList = new ArrayList<>();
        d.iterator().forEachRemaining(doctorEntityList::add);
        return new ResponseEntity<>(doctorEntityList, HttpStatus.OK);

    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorEntity> deleteDoctor(@RequestParam("id") Integer id) {
        if (!doctorRepository.exists(id))
            return null;
        else {
            doctorRepository.delete(id);
            return new ResponseEntity<>(doctorRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorEntity> getDoctorById(@RequestParam("id") Integer id) {

        DoctorEntity c = doctorRepository.findOne(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }
}
