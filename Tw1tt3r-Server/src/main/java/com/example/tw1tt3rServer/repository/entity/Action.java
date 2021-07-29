package com.example.tw1tt3rServer.repository.entity;

import com.example.tw1tt3rServer.repository.entity.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Action {

    //------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Person sourcePerson;

    @ManyToOne
    private Person destinationPerson;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column
    private boolean isNotified;

    //------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return ("source: " + sourcePerson.getUserName() + " destination: " + destinationPerson.getUserName() + " " +
                "Action Type: " + actionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Action))
            return false;

        Action other = (Action) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
