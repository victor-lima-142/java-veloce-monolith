package br.veloce.api.domain.models.vehicle;

import br.veloce.api.domain.enums.vehicle.VehicleColor;
import br.veloce.api.domain.enums.vehicle.VehicleStatus;
import br.veloce.api.domain.enums.vehicle.VehicleType;
import br.veloce.api.domain.models.company.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
@SQLDelete(sql = "UPDATE vehicles SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private VehicleType type;

    @Column(name = "year_manufacture", nullable = false)
    private Integer yearManufacture;

    @Column(name = "year_model", nullable = false)
    private Integer yearModel;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "chassis_number", nullable = false, unique = true, length = 17)
    private String chassisNumber;

    @Column(name = "market_reference", nullable = false)
    private String marketReference;

    @Column(name = "market_value", nullable = false)
    private Long marketValue;

    @Column(name = "value", nullable = false)
    private Long value;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false, length = 20)
    private VehicleColor color;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false, unique = true)
    private InventoryItem inventoryItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private CarSpecification carSpecification;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private MotoSpecification motoSpecification;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @Builder.Default
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt = null;
}
