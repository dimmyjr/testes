package crud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {
    public static final String JOVEM = "JOVEM";
    public static final String ADULTO = "ADULTO";
    public static final String IDOSO = "IDOSO";

    private UUID id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private LocalDate birthDate;

    public String fullName() {
        return this.firstName + " " + this.lastName;
    }

    @JsonIgnore
    public int getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    @JsonIgnore
    public String getAgeGroup() {
        final int age = this.getAge();

        if (age <= 19) {
            return JOVEM;
        } else if (age > 19 && age <= 59) {
            return ADULTO;
        } else {
            return IDOSO;
        }
    }
}
