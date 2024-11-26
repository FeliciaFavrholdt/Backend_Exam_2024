package dk.studiofavrholdt.daos.impl;

import dk.studiofavrholdt.daos.IDAO;
import dk.studiofavrholdt.dtos.ItemDTO;
import dk.studiofavrholdt.dtos.StudentDTO;
import dk.studiofavrholdt.entities.Item;
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
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemDAO implements IDAO<ItemDTO, Integer> {
    private static ItemDAO instance;
    private static EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(ItemDAO.class);

    public ItemDAO(EntityManagerFactory emf) {
        ItemDAO.emf = emf;
    }

    /**
     * Singleton pattern
     * Ensure only one instance of the DAO is created
     */
    public static ItemDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ItemDAO();
        }
        return instance;
    }

    @Override
    public Optional<ItemDTO> get(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Item item = em.find(Item.class, id);
            if (item == null) {
                logger.warn("Item with ID {} not found", id);
                return Optional.empty();
            }
            return Optional.of(new ItemDTO(item));
        } catch (Exception e) {
            logger.error("Error fetching Item with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ItemDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Item> query = em.createQuery("SELECT i FROM Item i", Item.class);
            List<Item> items = query.getResultList();
            return items.stream().map(ItemDTO::new).toList();
        } catch (Exception e) {
            logger.error("Error fetching all Items: {}", e.getMessage());
            throw e;
        }
    }



    @Override
    public ItemDTO create(ItemDTO itemDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Item item = new Item(itemDTO); // Convert DTO to Entity
            if (itemDTO.getStudentId() != null) { // If Student ID is provided
                Student student = em.find(Student.class, itemDTO.getStudentId());
                if (student == null) {
                    throw new IllegalArgumentException("Student with ID " + itemDTO.getStudentId() + " not found");
                }
                item.setStudent(student); // Associate the Student
            }

            em.persist(item);
            em.getTransaction().commit();
            return new ItemDTO(item);
        } catch (Exception e) {
            logger.error("Error creating Item: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ItemDTO update(Integer id, ItemDTO itemDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Item item = em.find(Item.class, id);
            if (item == null) {
                logger.warn("Item with ID {} not found", id);
                throw new IllegalArgumentException("Item not found");
            }

            // Update fields
            item.setName(itemDTO.getName());
            item.setPurchasePrice(itemDTO.getPurchasePrice());
            item.setCategory(itemDTO.getCategory());
            item.setAcquisitionDate(itemDTO.getAcquisitionDate());
            item.setDescription(itemDTO.getDescription());

            // Update associated Student if provided
            if (itemDTO.getStudentId() != null) {
                Student student = em.find(Student.class, itemDTO.getStudentId());
                if (student == null) {
                    throw new IllegalArgumentException("Student with ID " + itemDTO.getStudentId() + " not found");
                }
                item.setStudent(student);
            }

            em.merge(item);
            em.getTransaction().commit();
            return new ItemDTO(item);
        } catch (Exception e) {
            logger.error("Error updating Item with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Item item = em.find(Item.class, id);
            if (item == null) {
                logger.warn("Item with ID {} not found", id);
                throw new IllegalArgumentException("Item not found");
            }

            em.remove(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error deleting Item with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void addItemToStudent(int itemId, int studentId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Item item = em.find(Item.class, itemId);
            if (item == null) {
                logger.warn("Item with ID {} not found", itemId);
                throw new IllegalArgumentException("Item not found");
            }

            Student student = em.find(Student.class, studentId);
            if (student == null) {
                logger.warn("Student with ID {} not found", studentId);
                throw new IllegalArgumentException("Student not found");
            }

            item.setStudent(student); // Associate the Student
            em.merge(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error adding Item with ID {} to Student with ID {}: {}", itemId, studentId, e.getMessage());
            throw e;
        }
    }

    public Set<ItemDTO> getItemsByStudent(int studentId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Item> query = em.createQuery(
                    "SELECT i FROM Item i WHERE i.student.studentId = :studentId", Item.class
            );
            query.setParameter("studentId", studentId);
            List<Item> items = query.getResultList();

            return items.stream().map(ItemDTO::new).collect(Collectors.toSet());
        } catch (Exception e) {
            logger.error("Error fetching Items for Student with ID {}: {}", studentId, e.getMessage());
            throw e;
        }
    }
}