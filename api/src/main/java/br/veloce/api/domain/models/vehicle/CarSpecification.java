package br.veloce.api.domain.models.vehicle;

import br.veloce.api.domain.enums.car.CarBodyType;
import br.veloce.api.domain.enums.car.CarEngineType;
import br.veloce.api.domain.enums.car.CarFuelType;
import br.veloce.api.domain.enums.car.CarTransmissionType;
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
@Table(name = "car_specifications")
@SQLDelete(sql = "UPDATE car_specifications SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class CarSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Column(name = "doors", nullable = false)
    private Integer doors;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel", nullable = false, length = 20)
    private CarFuelType fuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine", nullable = false, length = 20)
    private CarEngineType engine;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission", nullable = false, length = 20)
    private CarTransmissionType transmission;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarBodyType bodyType = CarBodyType.OTHER;

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
