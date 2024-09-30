package org.severinu.demoapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.severinu.demoapi.api.entity.Person;
import org.severinu.demoapi.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class DemoApiApplicationTests {

    @Autowired
    private PersonService  personService;

    @Test
    void testGetAll_shouldReturnNotEmptyList() {
        List<Person> all = personService.getAll();

        Assertions.assertFalse(all.isEmpty());
    }

}
