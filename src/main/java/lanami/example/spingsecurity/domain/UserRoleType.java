package lanami.example.spingsecurity.domain;

import java.io.Serializable;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */
public enum UserRoleType implements Serializable {
    USER("USER"),
    MANAGER ("MANAGER"),
    ADMIN ("ADMIN");

    String value;

    private UserRoleType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
