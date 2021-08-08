package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entities.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        scope = RoomDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class RoomDto extends Dto{

    @JsonProperty("Id")
    private int Id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("members")
    private Set<PersonDto> members = new HashSet<PersonDto>();

    @JsonProperty("messages")
    private Set<MessageDto> messages = new HashSet<MessageDto>();

    @JsonProperty("roomType")
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RoomDto))
            return false;

        RoomDto other = (RoomDto) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public RoomDto(){

    }


    public Timestamp lastMessageTimeStamp(){
        Timestamp timestamp = new Timestamp(0);
        for(MessageDto message:messages){
            if(timestamp.compareTo(message.getTimestamp()) < 0){
                timestamp = message.getTimestamp();
            }
        }
        return(timestamp);
    }

}
