package br.veloce.api.domain.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InventoryItemStoreId {
    @Column(name = "inventory_item_id")
    private String inventoryItemId;

    @Column(name = "store_id")
    private String storeId;
}
