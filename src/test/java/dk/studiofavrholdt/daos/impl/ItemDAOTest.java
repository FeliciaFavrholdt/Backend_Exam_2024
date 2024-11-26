package dk.studiofavrholdt.daos.impl;

import dk.studiofavrholdt.config.HibernateConfig;
import dk.studiofavrholdt.dtos.ItemDTO;
import dk.studiofavrholdt.dtos.StudentDTO;
import dk.studiofavrholdt.enums.Category;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemDAOTest {

    private EntityManagerFactory emf;
    private ItemDAO itemDAO;
    private StudentDAO studentDAO;

    @BeforeAll
    void setup() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        itemDAO = ItemDAO.getInstance(emf);
        studentDAO = StudentDAO.getInstance(emf);
    }

    @BeforeEach
    void populateDatabase() {
        // Clear database and insert mock data before each test
        studentDAO.create(new StudentDTO(null, "Henriette Madsen", "Henriette@example.com", LocalDate.now(), "123-456-7890"));
        studentDAO.create(new StudentDTO(null, "Bobby Wilson", "Bobby@example.com", LocalDate.now(), "987-654-3210"));

        itemDAO.create(new ItemDTO(null, "Printer", 1500.0, Category.PRINT, LocalDate.now(), "Gaming Laptop"));
        itemDAO.create(new ItemDTO(null, "3D glasses", 200.0, Category.VR, LocalDate.now(), "Office Desk"));
    }

    @Test
    void testGetItemById() {
        Optional<ItemDTO> item = itemDAO.get(1);
        assertTrue(item.isPresent());
        assertEquals("Printer", item.get().getName());
    }

    @Test
    void testGetAllItems() {
        List<ItemDTO> items = itemDAO.getAll();
        assertEquals(2, items.size());
    }

    @Test
    void testAddItemToStudent() {
        itemDAO.addItemToStudent(1, 1); // Assign printer to Henriette Madsen
        Set<ItemDTO> HenriettesItems = itemDAO.getItemsByStudent(1);
        assertEquals(1, HenriettesItems.size());
        assertEquals("Laptop", HenriettesItems.iterator().next().getName());
    }

    @Test
    void testGetItemsByStudent() {
        itemDAO.addItemToStudent(1, 1); // Assign Printer to Bobby Wilson
        itemDAO.addItemToStudent(2, 1); // Assign 3D glasses to Bobby Wilson

        Set<ItemDTO> BobbysItems = itemDAO.getItemsByStudent(1);
        assertEquals(2, BobbysItems.size());
    }

    @Test
    void testDeleteItem() {
        itemDAO.delete(1);
        Optional<ItemDTO> item = itemDAO.get(1);
        assertFalse(item.isPresent());
    }

    @AfterAll
    void tearDown() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}