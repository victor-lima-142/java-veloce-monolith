package br.veloce.api.domain.models.vehicle;


import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.embeddable.InventoryItemStoreId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_item_stores")
@SQLDelete(sql = "UPDATE inventory_item_stores SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class InventoryItemStore {
    @EmbeddedId
    private InventoryItemStoreId id;

    @MapsId("inventoryItemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @MapsId("storeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}
