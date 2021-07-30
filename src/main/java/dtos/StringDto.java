package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StringDto extends Dto{
//    @SerializedName("string")
    @JsonProperty("string")
    String string;

    public StringDto(String string) {
        this.string = string;
    }

    public StringDto(){

    }
}
