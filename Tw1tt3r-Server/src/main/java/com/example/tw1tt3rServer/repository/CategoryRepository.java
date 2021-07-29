package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Category;
import com.example.tw1tt3rServer.repository.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByNameAndSourcePerson(String name, Person sourcePerson);
    public boolean existsByName(String name);
}
