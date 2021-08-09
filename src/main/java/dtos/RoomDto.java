package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entities.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        resolver = Resolver.class,
        scope = RoomDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class RoomDto extends Dto {

    @JsonProperty("Id")
    private int Id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("messages")
    private List<MessageDto> messages = new LinkedList<MessageDto>();

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

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
