package com.example.tw1tt3rServer.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {

    public Category(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "sourcePerson_id")
    private Person sourcePerson;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "includedCategories")
    private Set<Person> destPeopleList = new HashSet<Person>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Category))
            return false;

        Category other = (Category) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PreRemove
    private void preRemoveFunction(){
        for(Person person:destPeopleList){
            person.getIncludedCategories().remove(this);
        }
        destPeopleList.clear();
    }
}
