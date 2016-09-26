package lanami.example.springsecurity;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

/**
 * @author lanami
 */
public abstract class Utils {

    /**
     * Map Bean Validation field error messages by field name.
     * */
    public static Map<String, List<String>> translate(List<FieldError> fieldErrors){

        return fieldErrors.stream().collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList())));
    }
}
