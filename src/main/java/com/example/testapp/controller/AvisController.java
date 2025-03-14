package com.example.testapp.controller;

import com.example.testapp.entities.Avis;
import com.example.testapp.services.AvisInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avis")

public class AvisController {
    @Autowired
    AvisInterface avisInterface;

    @GetMapping("/add")
    public Avis addAvis(@RequestBody Avis avis){
        return avisInterface.addAvis(avis);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        avisInterface.deleteAvis(id);
    }
    @PatchMapping("/update/{id}")
    public Avis udpateAvis(@PathVariable Long id, @RequestBody Avis avis){
        return avisInterface.updateAvis(id, avis);
    }
    @GetMapping("/getAllAvis")
    public List<Avis> getAllAvis(){
        return avisInterface.getAllAvis();
    }
    @GetMapping("/getAvisById/{id}")
    public Avis getAvisById(@PathVariable Long id){
        return avisInterface.getAvisById(id);
    }
}
