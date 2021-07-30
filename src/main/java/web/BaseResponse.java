package web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import dtos.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
//    @SerializedName("responseHeader")
    @JsonProperty("responseHeader")
    private ResponseHeader responseHeader;

//    @SerializedName("dto")
    @JsonProperty("dto")
    private Dto dto;

    public BaseResponse(ResponseHeader responseHeader, Dto dto){
        this.responseHeader = responseHeader;
        this.dto = dto;
    }

    public BaseResponse(){

    }
}
