package org.severinu.demoapi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.entity.Person;
import org.severinu.demoapi.api.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        log.info("[GET] getAllPeople");
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getOnePersonById(@PathVariable(name = "id") int id) {
        log.info("[GET] getOnePersonById/{}", id);
        return ResponseEntity.ok(personService.getOne(id));
    }
}
