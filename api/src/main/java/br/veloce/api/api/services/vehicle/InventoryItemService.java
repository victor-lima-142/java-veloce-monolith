package br.veloce.api.api.services.vehicle;

import br.veloce.api.api.dtos.PagedResponse;
import br.veloce.api.api.dtos.vehicle.VehicleSummary;
import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.vehicle.InventoryItem;
import br.veloce.api.domain.models.vehicle.Vehicle;
import br.veloce.api.domain.repositories.vehicle.InventoryItemStoreRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryItemService {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemService.class);
    private final InventoryItemStoreRepository inventoryItemStoreRepository;

    @Transactional(readOnly = true)
    public PagedResponse<VehicleSummary> findInventoryOfStore(Store store, Pageable pageable) {
        Page<InventoryItem> items = inventoryItemStoreRepository.findItemsOfStore(store.getId(), pageable);
        var finalPage = items.map(item -> new VehicleSummary(item.getVehicle()));
        return new PagedResponse<>(finalPage);
    }
}
