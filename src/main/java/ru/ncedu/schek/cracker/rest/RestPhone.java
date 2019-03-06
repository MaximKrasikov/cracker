package ru.ncedu.schek.cracker.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ncedu.schek.cracker.entities.Model;
import ru.ncedu.schek.cracker.entities.Phone;
import ru.ncedu.schek.cracker.repository.ModelRepository;
import ru.ncedu.schek.cracker.repository.ModelService.ModelService;
import ru.ncedu.schek.cracker.repository.PhoneRepository;
import ru.ncedu.schek.cracker.repository.PhoneService.PhoneService;

/**
 * Created by Admin on 06.03.2019.
 */
@RestController
public class RestPhone {
    @Autowired
    ModelService modelService;
    @Autowired
    PhoneService phoneService;
    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    ModelRepository modelRepository;
    //-------------------Delete a Phone--------------------------------------------------------
    @RequestMapping(value = "/phones/{phoneId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePhone(@PathVariable("phoneId") long phoneId){
        Phone phone = phoneService.findById(phoneId);
        int count= phone.getModel().getPhones().size();
        if (phone.getModel().getPhones().size()==1){
            Model model = modelService.findByName(phone.getModel().getModelName());
            phoneService.deleteById(phoneId);
            modelRepository.delete(model);
        }else if(phone.getModel().getPhones().size()>1){
            phoneService.deleteById(phoneId);
        }
        if (phone == null){
            System.out.println("Unable to delete  Phone with id "+phoneId+ " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}