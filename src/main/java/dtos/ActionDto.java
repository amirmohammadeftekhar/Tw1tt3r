package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entities.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        resolver = Resolver.class,
        scope = ActionDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class ActionDto extends Dto  {
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("sourcePerson")
    private PersonDto sourcePerson;

    @JsonProperty("destinationPerson")
    private PersonDto destinationPerson;

    @JsonProperty("actionType")
    private ActionType actionType;

    @JsonProperty("isNotified")
    private boolean isNotified;

    public ActionDto(){

    }

    @Override
    public String toString() {
        return ("source: " + sourcePerson.getUserName() + " destination: " + destinationPerson.getUserName() + " " +
                "Action Type: " + actionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ActionDto))
            return false;

        ActionDto other = (ActionDto) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
