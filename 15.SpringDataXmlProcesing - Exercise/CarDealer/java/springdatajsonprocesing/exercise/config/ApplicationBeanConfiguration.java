package springdatajsonprocesing.exercise.config;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springdatajsonprocesing.exercise.utils.ValidatorUtil;
import springdatajsonprocesing.exercise.utils.ValidatorUtilImpl;
import springdatajsonprocesing.exercise.utils.XmlParser;
import springdatajsonprocesing.exercise.utils.XmlParserImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
                        return LocalDateTime
                                .parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss"));
                    }})
                .create();
    }

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }
}
