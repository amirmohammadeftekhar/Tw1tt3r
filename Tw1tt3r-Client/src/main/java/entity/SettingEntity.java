package entity;

import dtos.Dto;
import entities.enums.LastSeenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class SettingEntity extends Dto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private int personId;

    @Column
    private boolean isPrivate;

    @Column
    private LastSeenType lastSeenType;

    @Column
    private String password;

    @Column
    private boolean toDelete;

    @Column
    private boolean toDeactivate = false;

    @Column
    private Timestamp timestamp;

    public SettingEntity(){

    }


}
