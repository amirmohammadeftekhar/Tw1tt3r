package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import entities.enums.LastSeenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id",
        scope = PersonDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class PersonDto extends Dto {
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("lastSeen")
    private Timestamp lastSeen;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @JsonProperty("toShowEmail")
    private boolean toShowEmail;

    @JsonProperty("token")
    private String token;

    @JsonProperty("picture")
    private PictureDto picture;

    @JsonProperty("lastSeenType")
    private LastSeenType lastSeenType;

    @JsonProperty("savedTweets")
    private Set<TweetDto> savedTweets = new HashSet<TweetDto>();

/*
    @JsonProperty("savedMessageRoom")
    private RoomDto savedMessageRoom;
*/

/*
    @JsonProperty("readMessages")
    private Set<MessageDto> readMessages = new HashSet<MessageDto>();
*/

    @JsonProperty("rooms")
    private Set<RoomDto> rooms = new HashSet<RoomDto>();

    @JsonProperty("whichTweetLiking")
    private Set<TweetDto> whichTweetLiking = new HashSet<TweetDto>();

    @JsonProperty("ownedCategories")
    private Set<CategoryDto> ownedCategories = new HashSet<CategoryDto>();


    @Override
    public String toString() {
        return ("Username: " + userName + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonDto))
            return false;

        PersonDto other = (PersonDto) o;

        return (Id == other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public PersonDto(){

    }

}
