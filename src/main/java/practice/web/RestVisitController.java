package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import practice.model.VisitEntity;
import practice.repository.VisitRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path = "/visit")
public class RestVisitController {
    @Autowired
    private VisitRepository visitRepository;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitEntity> addNewVisit(VisitEntity visitEntity) {
        VisitEntity v = visitRepository.save(visitEntity);
        return new ResponseEntity<>(v, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitEntity>> getAllUsers() {

        Iterable<VisitEntity> v = visitRepository.findAll();
        List<VisitEntity> visitEntityList = new ArrayList<>();
        v.iterator().forEachRemaining(visitEntityList::add);
        return new ResponseEntity<>(visitEntityList, HttpStatus.OK);

    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitEntity> deleteVisit(@RequestParam("id") Integer id) {
        if (!visitRepository.exists(id))
            return null;
        else {
            visitRepository.delete(id);
            return new ResponseEntity<>(visitRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitEntity> getVisitById(@RequestParam("id") Integer id) {

        VisitEntity c = visitRepository.findOne(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }
}
