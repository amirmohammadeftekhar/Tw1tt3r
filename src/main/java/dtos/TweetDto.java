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
        property = "Id",
        resolver = Resolver.class,
        scope = TweetDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class TweetDto extends Dto {
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("parentTweet")
    private TweetDto parentTweet;

    @JsonProperty("personWhoMageThis")
    private PersonDto personWhoMadeThis;

    @JsonProperty("picture")
    private PictureDto picture;

    @JsonProperty("whoLiked")
    private Set<PersonDto> whoLiked = new HashSet<PersonDto>();

    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("rootPerson")
    private PersonDto rootPerson;

    @JsonProperty("whoReports")
    private Set<PersonDto> whoReports = new HashSet<PersonDto>();

    @JsonProperty("peopleWhoSaved")
    private Set<PersonDto> peopleWhoSaved = new HashSet<PersonDto>();


    @Override
    public String toString() {
        return (personWhoMadeThis.getUserName() + " Wrote this: " + text + "\n" + "Id: " + Integer.toString(Id) + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TweetDto))
            return false;

        TweetDto other = (TweetDto) o;

        return (Id == other.getId());
    }

    public TweetDto(){

    }

}











