package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);

        if (person.isEmpty()) return 0;
        else db.remove(person.get());

        return 1;
    }

    @Override
    public int updatePersonByID(UUID id, Person newPerson) {
        return selectPersonById(id).map(person -> {
            int indexOfPersonToUpdate = db.indexOf(person);
            if (indexOfPersonToUpdate >= 0) {
                db.set(indexOfPersonToUpdate, new Person(id, newPerson.getName()));
                return 1;
            }

            return 0;
        }).orElse(0);
    }
}
