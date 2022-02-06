package com.bilgeadam.odevpersonelapp.controller;

import com.bilgeadam.odevpersonelapp.entity.Personel;
import com.bilgeadam.odevpersonelapp.exception.PersonelNotFoundException;
import com.bilgeadam.odevpersonelapp.repository.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonelController {

    @Autowired
    private PersonelRepository perseonelRepository;

    @GetMapping("/personel/{id}")
    public Personel getKisi(@PathVariable("id") long id)
    {
        Personel personel = null;
        Optional<Personel> personelDB = perseonelRepository.findById(id);
        if(personelDB.isPresent())
        {
            personel = personelDB.get();
            return personel;
        }
        else
        {
            throw new PersonelNotFoundException(id + " nolu Personel Bulunamadı");
        }
    }
    @GetMapping("/personel")
    public List<Personel> getTumPersonel()
    {

        return perseonelRepository.findAll();
    }
    @GetMapping("/personel-tam/{id}")
    public String getPersonelTam(@PathVariable("id") long id)
    {
        Personel personel = getKisi(id);
        String bolumAd = getBolumAd(personel.getBolumNo());
        return personel.getAd()+ " "+personel.getSoyad() +" "+ bolumAd;
    }


    private String getBolumAd(long id)
    {

        String bolumURL = "http://localhost:8230";

        RestTemplate restTemplate = new RestTemplate();

        String bolumAd = restTemplate.getForObject(bolumURL + "/bolumad/" + id, String.class);

        return bolumAd;
    }
}
