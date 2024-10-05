package org.severinu.demoapi.utils;

import org.severinu.demoapi.api.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonUtils {

    private PersonUtils() {
    }

    public static List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(1, "Adam", "Kowalski"));
        people.add(new Person(2, "Mike", "Smith"));
        people.add(new Person(3, "Teddy", "Kornik"));
        people.add(new Person(4, "Anna", "Flowers"));
        return people;
    }

    public static Person getOnePerson(int id) {
        List<Person> allPeople = getAllPeople();
        Optional<Person> personOptional = allPeople.stream().filter(p -> p.getId() == id).findFirst();
        return personOptional.orElse(null);
    }

}
