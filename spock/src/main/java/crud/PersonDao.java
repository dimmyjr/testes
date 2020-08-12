package crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class PersonDao {

    private final ObjectMapper mapper;

    @SneakyThrows
    public String save(final Person person) {
        person.setId(UUID.randomUUID());
        final String jsonString = this.mapper.writeValueAsString(person);
        System.out.println(jsonString);
        return person.getId().toString();
    }

    @SneakyThrows
    public String save2(final Person person) {

        if (person.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name must be filled");
        }

        if (person.getBirthDate() != null && person.getBirthDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("The date cannot be more than today");
        }

        person.setId(UUID.randomUUID());
        final String jsonString = this.mapper.writeValueAsString(person);
        System.out.println(jsonString);
        return person.getId().toString();
    }
}
