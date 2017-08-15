package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.PatientEntity;
import practice.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path = "/patient")
public class RestPatientController {
    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientEntity> addNewPatient(PatientEntity patientEntity) {
        PatientEntity p = patientRepository.save(patientEntity);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientEntity>> getAllUsers() {
        Iterable<PatientEntity> p = patientRepository.findAll();
        List<PatientEntity> patientEntityList = new ArrayList<>();
        p.iterator().forEachRemaining(patientEntityList::add);
        return new ResponseEntity<>(patientEntityList, HttpStatus.OK);

    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientEntity> deletePatient(@RequestParam("id") Integer id) {
        if (!patientRepository.exists(id))
            return null;

        else {
            patientRepository.delete(id);
            return new ResponseEntity<>(patientRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientEntity> getPatientById(@RequestParam("id") Integer id) {


        PatientEntity p = patientRepository.findOne(id);

        return new ResponseEntity<>(p, HttpStatus.OK);

    }
}

