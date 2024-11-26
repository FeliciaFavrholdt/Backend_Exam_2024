package dk.studiofavrholdt.dtos;

import dk.studiofavrholdt.entities.Student;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDTO {
    private Integer id;
    private String name;
    private String email;
    private LocalDate enrollmentDate;
    private String phone;

    public StudentDTO(Student student) {
        this.id = student.getStudentId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.enrollmentDate = student.getEnrollmentDate();
        this.phone = student.getPhone();
    }
}
