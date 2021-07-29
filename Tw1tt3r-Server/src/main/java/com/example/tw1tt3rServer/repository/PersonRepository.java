package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByActiveStateIsTrueAndDeletedIsFalse();

    Person findPersonByUserNameAndActiveStateIsTrueAndDeletedIsFalse(String userName);

    Person findPersonByUserNameAndDeletedIsFalse(String userName);

    boolean existsPersonByUserNameAndDeletedIsFalse(String userName);

    boolean existsPersonByEmailAddressAndDeletedIsFalse(String emailAddress);

    @Query("select p from Person p where p.userName = ?1 and p.password = ?2")
    Optional<Person> login(String userName, String password);

    Optional<Person> findByToken(String token);
}
