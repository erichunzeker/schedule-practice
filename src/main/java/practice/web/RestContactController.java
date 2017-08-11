package practice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import practice.model.ContactEntity;
import practice.model.PatientEntity;
import practice.repository.ContactRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XHTML_XML_VALUE;

/**
 * Created by ehunzeker on 5/15/17.
 */
@Controller
@RequestMapping(path="/rest/contact")
public class RestContactController
{
    @Autowired
    private ContactRepository contactRepository;

    //Add contact using HTTP Post at /add
    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<ContactEntity> addNewContact(ContactEntity contactEntity) {

        ContactEntity c = contactRepository.save(contactEntity);
        return new ResponseEntity<>(c, HttpStatus.OK);

    }

    //Retrieve all contacts in database
    @GetMapping(path = "/all", produces = APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<List<ContactEntity>> getAllUsers()
    {

        Iterable<ContactEntity> c = contactRepository.findAll();
        List<ContactEntity> contactEntityList = new ArrayList<>();
        c.iterator().forEachRemaining(contactEntityList::add);
        return new ResponseEntity<>(contactEntityList, HttpStatus.OK);

    }

    //HTTP delete specified contact by ID
    @RequestMapping(path = "/delete", method = RequestMethod.GET, produces = APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<ContactEntity> deleteContact(@RequestParam("id") Integer id)
    {
        if(!contactRepository.exists(id))
            return null;
        else
        {
            contactRepository.delete(id);
            return new ResponseEntity<>(contactRepository.findOne(id), HttpStatus.NO_CONTENT);
        }
    }

    //find contact by specified ID
    @GetMapping(path = "/find", produces = APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<ContactEntity> getContactById(@RequestParam("id") Integer id)
    {

        ContactEntity c = contactRepository.findOne(id);

        return new ResponseEntity<>(c, HttpStatus.OK);

    }

}


