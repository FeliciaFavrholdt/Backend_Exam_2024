package dk.studiofavrholdt.dtos;

import dk.studiofavrholdt.enums.Category;
import lombok.*;
import dk.studiofavrholdt.entities.Item;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDTO {
    private Integer id;
    private String name;
    private Double purchasePrice;
    private Category category;
    private LocalDate acquisitionDate;
    private String description;

    public ItemDTO(Item item) {
        this.id = item.getItemId();
        this.name = item.getName();
        this.purchasePrice = item.getPurchasePrice();
        this.category = item.getCategory();
        this.acquisitionDate = item.getAcquisitionDate();
        this.description = item.getDescription();
    }
}