package br.veloce.api.domain.models.company;

import br.veloce.api.domain.enums.UserRole;
import br.veloce.api.domain.models.embeddable.UserStoreId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user_stores",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "store_id"})
)
public class UserStore {

    @EmbeddedId
    private UserStoreId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("storeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_override", length = 30)
    private UserRole roleOverride;
}