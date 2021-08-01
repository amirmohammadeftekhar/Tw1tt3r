package utility;


import lombok.Getter;
import org.modelmapper.ModelMapper;

public class ModelMapperInstance {

    @Getter
    private static final ModelMapper modelMapper = new ModelMapper();
}