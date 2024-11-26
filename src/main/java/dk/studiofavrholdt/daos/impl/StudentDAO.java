package dk.studiofavrholdt.daos.impl;

import dk.studiofavrholdt.daos.IDAO;
import dk.studiofavrholdt.dtos.StudentDTO;
import dk.studiofavrholdt.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class StudentDAO implements IDAO<StudentDTO, Integer> {

    private static StudentDAO instance;
    private static EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(StudentDAO.class);

    public StudentDAO(EntityManagerFactory emf) {
        StudentDAO.emf = emf;
    }

    /**
     * Singleton pattern
     * Ensure only one instance of the DAO is created
     */
    public static StudentDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentDAO();
        }
        return instance;
    }

    @Override
    public Optional<StudentDTO> get(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Student student = em.find(Student.class, id);
            if (student == null) {
                logger.warn("Student with ID {} not found", id);
                return Optional.empty();
            }
            return Optional.of(new StudentDTO(student));
        } catch (Exception e) {
            logger.error("Error fetching Student with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<StudentDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            List<Student> students = query.getResultList();
            return students.stream().map(StudentDTO::new).toList();
        } catch (Exception e) {
            logger.error("Error fetching all Students: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            Student student = new Student(studentDTO);
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return new StudentDTO(student);
        } catch (Exception e) {
            logger.error("Error creating Student: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public StudentDTO update(Integer id, StudentDTO studentDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student == null) {
                logger.warn("Student with ID {} not found", id);
                throw new IllegalArgumentException("Student not found");
            }
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setEnrollmentDate(studentDTO.getEnrollmentDate());
            student.setPhone(studentDTO.getPhone());
            em.merge(student);
            em.getTransaction().commit();
            return new StudentDTO(student);
        } catch (Exception e) {
            logger.error("Error updating Student with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student == null) {
                logger.warn("Student with ID {} not found", id);
                throw new IllegalArgumentException("Student not found");
            }
            em.remove(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error deleting Student with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
