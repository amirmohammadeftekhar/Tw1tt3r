package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Action;
import com.example.tw1tt3rServer.repository.entity.Person;
import entities.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
    @Query(value = "select a from Action a where a.actionType=?1 and a.sourcePerson=?2 and a.sourcePerson" +
            ".activeState=true and a.sourcePerson.deleted=false and a.destinationPerson.activeState=true and a" +
            ".destinationPerson.deleted=false")
    public List<Action> findByActionTypeAndSourcePerson(ActionType actionType, Person sourcePerson);

    @Query(value = "select a from Action a where a.actionType=?1 and a.destinationPerson=?2 and a.sourcePerson" +
            ".activeState=true and a.sourcePerson.deleted=false and a.destinationPerson.activeState=true and a" +
            ".destinationPerson.deleted=false")
    public List<Action> findByActionTypeAndDestinationPerson(ActionType actionType, Person destinationPerson);

    @Query(value = "select a from Action a where a.actionType=?1 and a.sourcePerson=?2 and a.destinationPerson=?3 and" +
            " a.sourcePerson.activeState=true and a.sourcePerson.deleted=false and a.destinationPerson" +
            ".activeState=true and a.destinationPerson.deleted=false")
    public Action findActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType actionType,
                                                                            Person sourcePerson,
                                                                            Person destinationPerson);

    @Query(value = "select case when count(a)>0 then true else false end from Action  a where a.actionType=?1 and a" +
            ".sourcePerson=?2 and a.destinationPerson=?3 and a.sourcePerson.activeState=true and a.sourcePerson" +
            ".deleted=false and a.destinationPerson.activeState=true and a.destinationPerson.deleted=false")
    public boolean existsActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType actionType,
                                                                               Person sourcePerson,
                                                                               Person destinationPerson);
}
