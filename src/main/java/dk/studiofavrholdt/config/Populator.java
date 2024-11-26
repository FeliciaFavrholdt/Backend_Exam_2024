package dk.studiofavrholdt.config;

import dk.studiofavrholdt.daos.impl.ItemDAO;
import dk.studiofavrholdt.daos.impl.StudentDAO;
import dk.studiofavrholdt.dtos.ItemDTO;
import dk.studiofavrholdt.dtos.StudentDTO;
import dk.studiofavrholdt.enums.Category;

import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Populator {

    private static final Logger logger = LoggerFactory.getLogger(Populator.class);

    private final EntityManagerFactory emf;
    private final ItemDAO itemDAO;
    private final StudentDAO studentDAO;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
        this.itemDAO = ItemDAO.getInstance(emf);
        this.studentDAO = StudentDAO.getInstance(emf);
    }

    /**
     * Populates the database with mock data.
     */
    public void populate() {
        logger.info("Starting database population...");

        // Clear existing data
        clearDatabase();

        // Add Students
        logger.info("Adding students...");
        StudentDTO henriette = studentDAO.create(new StudentDTO(null, "Henriette Madsen", "Henriette@example.com", LocalDate.now(), "123-456-7890"));
        StudentDTO bobby = studentDAO.create(new StudentDTO(null, "Bobby Wilson", "Bobby@example.com", LocalDate.now(), "987-654-3210"));

        // Add Items and assign them to Students
        logger.info("Adding and assigning items...");
        ItemDTO tool = itemDAO.create(new ItemDTO(null, "Tool", 1500.0, Category.TOOL, LocalDate.now(), "Some Tool"));
        itemDAO.addItemToStudent(tool.getId(), henriette.getId());

        ItemDTO video = itemDAO.create(new ItemDTO(null, "Video", 200.0, Category.VIDEO, LocalDate.now(), "A video - watch it"));
        itemDAO.addItemToStudent(video.getId(), henriette.getId());

        ItemDTO sound = itemDAO.create(new ItemDTO(null, "Sound", 100.0, Category.SOUND, LocalDate.now(), "Listen to this"));
        itemDAO.addItemToStudent(sound.getId(), bobby.getId());

        logger.info("Database populated successfully!");
    }

    /**
     * Clears the database by removing all Students and Items.
     */
    private void clearDatabase() {
        logger.info("Clearing the database...");
        try {
            itemDAO.getAll().forEach(item -> {
                logger.debug("Deleting item: {}", item);
                itemDAO.delete(item.getId());
            });
            studentDAO.getAll().forEach(student -> {
                logger.debug("Deleting student: {}", student);
                studentDAO.delete(student.getId());
            });
            logger.info("Database cleared successfully.");
        } catch (Exception e) {
            logger.error("Error clearing database: {}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        logger.info("Initializing Populator...");
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        try {
            Populator populator = new Populator(emf);
            populator.populate();
        } catch (Exception e) {
            logger.error("An error occurred while populating the database: {}", e.getMessage(), e);
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
                logger.info("EntityManagerFactory closed.");
            }
        }
    }
}
