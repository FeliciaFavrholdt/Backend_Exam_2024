package dk.studiofavrholdt.entities;

import dk.studiofavrholdt.dtos.ItemDTO;
import dk.studiofavrholdt.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/***
 * 2.2 Implement an Item entity class with these properties:
 * id, name, purchasePrice, category, acquisitionDate, description. Use an enum for the category.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, unique = true)
    private Integer itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "acquisition_date", nullable = false)
    private LocalDate acquisitionDate;

    @Column(name = "description", nullable = false)
    private String description;

    // Many Items can be associated with one Student object. Bidirectional relationship
    // The JoinColumn annotation is used to specify the foreign key column in the items table.
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Item(String name, Double purchasePrice, Category category, LocalDate acquisitionDate, String description) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.category = category;
        this.acquisitionDate = acquisitionDate;
        this.description = description;
    }

    // Constructor for converting an ItemDTO to an Item object
    public Item(ItemDTO itemDTO) {
        this.itemId = itemDTO.getId();
        this.name = itemDTO.getName();
        this.purchasePrice = itemDTO.getPurchasePrice();
        this.category = itemDTO.getCategory(); // Enum directly assigned
        this.acquisitionDate = itemDTO.getAcquisitionDate();
        this.description = itemDTO.getDescription();
    }
}