package lanami.example.spingsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lana Kolupaeva
 */
public abstract class Utils {

    public static List<String> translate(List<FieldError> fieldErrors){

        List<String> res = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            res.add(String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return res;


        //for that lucky Tomcat running Java 8...
        //        return fieldErrors.stream().map(e -> {
        //            String messageCode = e.getCodes()[0];
        //            return messageSource.getMessage(messageCode, new String[]{}, Locale.getDefault());
        //        }).collect(Collectors.toList());

    }
}
