package dk.studiofavrholdt.entities;

import dk.studiofavrholdt.dtos.StudentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/***
 * 2.3 Implement a Student entity class with these properties:
 * id, name, email, enrollmentDate, phone, and a OneToMany relationship to items.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = "items") // Exclude items to prevent circular references in toString
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "phone", nullable = false)
    private String phone;

    /***
     * One Student can be associated with multiple Item objects. Bidirectional relationship
     * The mappedBy attribute tells Hibernate that the Item entity has a field (or property) named student, which is the owner of the relationship.
     * FetchType.LAZY means that the items collection will only be loaded from the database when accessed for the first time (on-demand loading).
     * CascadeType.ALL means that all operations (including remove) should be cascaded to the items collection.
     * OrphanRemoval = true means that when an item is removed from the items collection, it should be deleted from the database.
     * A Set is used here to ensure that there are no duplicate Item objects associated with a single Student.
     */

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();

    public Student(String name, String email, LocalDate enrollmentDate, String phone) {
        this.name = name;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
        this.phone = phone;
    }

    /***
     * The purpose of this constructor is to convert a StudentDTO object
     * into a Student entity by copying the relevant data fields.
     * This is common in systems where DTOs are used to transfer
     * data between layers (e.g., from a client to a server) while
     * entities represent the actual data model in the database.
     */
    public Student(StudentDTO studentDTO) {
        this.studentId = studentDTO.getId();
        this.name = studentDTO.getName();
        this.email = studentDTO.getEmail();
        this.enrollmentDate = studentDTO.getEnrollmentDate();
        this.phone = studentDTO.getPhone();
    }
}