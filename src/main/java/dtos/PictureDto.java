package dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id")
@Getter
@Setter
public class PictureDto extends Dto{
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("content")
    private byte[] content;

    public PictureDto(int id, byte[] content) {
        Id = id;
        this.content = content;
    }

    public PictureDto(){

    }
}
