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
        @JsonSubTypes.Type(value = PersonIniDto.class, name = "PersonIniDto")
})
public abstract class Dto {
    public Dto(){

    }
}
