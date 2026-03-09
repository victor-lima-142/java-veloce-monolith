package br.veloce.api.domain.models.vehicle;

import br.veloce.api.domain.enums.motorcycle.MotoEngineType;
import br.veloce.api.domain.enums.motorcycle.MotoFuelType;
import br.veloce.api.domain.enums.motorcycle.MotoTransmissionType;
import br.veloce.api.domain.enums.motorcycle.MotorcycleBodyType;
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
@Table(name = "moto_specifications")
@SQLDelete(sql = "UPDATE moto_specifications SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class MotoSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false, length = 30)
    private MotorcycleBodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel", nullable = false, length = 20)
    private MotoFuelType fuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine", nullable = false, length = 20)
    private MotoEngineType engine;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission", nullable = false, length = 20)
    private MotoTransmissionType transmission;

    @Column(name = "engine_cc")
    private Integer engineCc;

    @Column(name = "has_sidecar")
    @Builder.Default
    private Boolean hasSidecar = false;

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
