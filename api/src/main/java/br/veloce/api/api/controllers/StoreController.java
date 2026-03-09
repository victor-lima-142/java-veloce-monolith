package br.veloce.api.api.controllers;

import br.veloce.api.api.dtos.PagedResponse;
import br.veloce.api.api.dtos.company.UserBasic;
import br.veloce.api.api.dtos.company.details.CompanyStoreDetail;
import br.veloce.api.api.dtos.vehicle.VehicleSummary;
import br.veloce.api.api.services.company.StoreService;
import br.veloce.api.api.services.vehicle.InventoryItemService;
import br.veloce.api.domain.models.company.Store;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'SALER')")
public class StoreController {
    private final StoreService storeService;
    private final InventoryItemService inventoryItemService;

    @GetMapping("/{store_id}/users")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<PagedResponse<UserBasic>> findUsers(
            @Valid @NotNull @NotBlank @PathVariable(name = "store_id") String storeId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        Store store = storeService.findOrThrow(storeId);
        Page<UserBasic> users = storeService.findUsersOfStore(store, Pageable.ofSize(size).withPage(page));
        return ResponseEntity.ok(new PagedResponse<>(users));
    }

    @GetMapping("/{store_id}")
    public ResponseEntity<CompanyStoreDetail> findDetail(
            @Valid @NotNull @NotBlank @PathVariable(name = "store_id") String storeId
    ) {
        Store store = storeService.findOrThrow(storeId);
        CompanyStoreDetail response = new CompanyStoreDetail(store);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{store_id}/inventory")
    public ResponseEntity<PagedResponse<VehicleSummary>> findVehicles(
            @Valid @NotNull @NotBlank @PathVariable(name = "store_id") String storeId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        Store store = storeService.findOrThrow(storeId);
        PagedResponse<VehicleSummary> summaries = inventoryItemService.findInventoryOfStore(store, Pageable.ofSize(size).withPage(page));
        return ResponseEntity.ok(summaries);
    }
}
