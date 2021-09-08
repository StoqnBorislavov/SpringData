package softuni.exam.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Validator;



public class ValidatorUtilImpl implements ValidationUtil{

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
       return this.validator.validate(entity).isEmpty();
    }
}
