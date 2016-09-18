package lanami.example.spingsecurity.controller.http;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-09
 */
public class ResponseEnvelope implements Serializable {
    private Object result;
    private List<String> errors;

    public ResponseEnvelope(){}

    private ResponseEnvelope(Object result) {
        this.result = result;
    }

    private ResponseEnvelope(List<String> errors) {
        this.errors = errors;
    }

    public static ResponseEnvelope failure(List<String> errors) {
        return new ResponseEnvelope(errors);
    }

    public static ResponseEnvelope success(Object result) {
        return new ResponseEnvelope(result);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
