package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entities.enums.LastSeenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        resolver = Resolver.class,
        scope = SettingEntityDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class SettingEntityDto extends Dto{
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("personId")
    private int personId;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("lastSeenType")
    private LastSeenType lastSeenType;

    @JsonProperty("password")
    private String password;

    @JsonProperty("toDelete")
    private boolean toDelete;

    @JsonProperty("toDeactivate")
    private boolean toDeactivate;

    public SettingEntityDto(){

    }
}























