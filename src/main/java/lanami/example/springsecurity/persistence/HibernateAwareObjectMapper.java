package lanami.example.springsecurity.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Implement Jackson {@link ObjectMapper} to fix LazyInitializationException which occurs on attempt to serialize
 * lazily initialized Hibernate collection to JSON.
 *
 * {@see com.fasterxml.jackson.annotation package for annatonations enabling control over JSON serialization}
 *
 * @author lanami
 * @date 2016-09-08
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
