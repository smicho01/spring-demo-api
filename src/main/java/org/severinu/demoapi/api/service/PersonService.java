package org.severinu.demoapi.api.service;

import org.severinu.demoapi.api.entity.Person;
import org.severinu.demoapi.utils.PersonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    public List<Person> getAll() {
        return PersonUtils.getAllPeople();
    }

    public Person getOne(int id) {
        return PersonUtils.getOnePerson(id);
    }

}
