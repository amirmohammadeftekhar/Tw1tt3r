package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id")
@Getter
@Setter
@AllArgsConstructor
public class MessageDto extends Dto{
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("sourcePerson")
    private PersonDto sourcePerson;

    @JsonProperty("destinationRoom")
    private RoomDto destinationRoom;

    @JsonProperty("text")
    private String text;

    @JsonProperty("isNotified")
    private boolean isNotified;

    @JsonProperty("picture")
    private PictureDto picture;

    @JsonProperty("whoHasRead")
    private Set<PersonDto> whoHasRead = new HashSet<PersonDto>();

    @Override
    public String toString() {
        return (sourcePerson.getUserName() + ":\n" + text + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MessageDto))
            return false;

        MessageDto other = (MessageDto) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public MessageDto(){

    }


}
