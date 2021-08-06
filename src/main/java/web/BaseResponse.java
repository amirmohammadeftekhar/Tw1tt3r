package web;

import com.fasterxml.jackson.annotation.JsonProperty;
import dtos.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    @JsonProperty("responseHeader")
    private ResponseHeader responseHeader;

    @JsonProperty("dto")
    private Dto dto;

    public BaseResponse(ResponseHeader responseHeader, Dto dto){
        this.responseHeader = responseHeader;
        this.dto = dto;
    }

    public BaseResponse(){

    }
}
