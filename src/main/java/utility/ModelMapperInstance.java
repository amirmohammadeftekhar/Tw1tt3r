package utility;


import lombok.Getter;
import org.modelmapper.ModelMapper;

public class ModelMapperInstance {
    private static ModelMapperInstance instance;

    @Getter
    private static ModelMapper modelMapper;

    public static ModelMapperInstance getInstance(){
        if(instance == null) instance = new ModelMapperInstance();
        return(instance);
    }

    public ModelMapperInstance(){
        modelMapper = new ModelMapper();
    }

}