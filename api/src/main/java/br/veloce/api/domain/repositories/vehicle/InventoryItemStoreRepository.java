package br.veloce.api.domain.repositories.vehicle;

import br.veloce.api.domain.models.vehicle.InventoryItem;
import br.veloce.api.domain.models.vehicle.InventoryItemStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemStoreRepository extends JpaRepository<InventoryItemStore, String> {
    @Query("""
        SELECT DISTINCT ii
        FROM InventoryItemStore iis
        JOIN iis.inventoryItem ii
        JOIN FETCH ii.vehicle v
        JOIN FETCH v.manufacturer
        JOIN FETCH v.createdUser
        LEFT JOIN FETCH v.carSpecification
        LEFT JOIN FETCH v.motoSpecification
        WHERE iis.store.id = :storeId
    """)
    Page<InventoryItem> findItemsOfStore(@Param("storeId") String storeId, Pageable pageable);
}
