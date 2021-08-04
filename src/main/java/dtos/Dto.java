package dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringDto.class, name = "StringDto"),
        @JsonSubTypes.Type(value = PersonDto.class, name = "PersonDto"),
        @JsonSubTypes.Type(value = RoomDto.class, name = "RoomDto"),
        @JsonSubTypes.Type(value = TweetListDto.class, name = "TweetListDto"),
        @JsonSubTypes.Type(value = TweetDto.class, name = "TweetDto"),
        @JsonSubTypes.Type(value = CategoryDto.class, name = "CategoryDto"),
        @JsonSubTypes.Type(value = PictureDto.class, name = "PictureDto"),
        @JsonSubTypes.Type(value = MessageDto.class, name = "MessageDto"),
        @JsonSubTypes.Type(value = PersonIniDto.class, name = "PersonIniDto")
})
public abstract class Dto {
    public Dto(){

    }
}
