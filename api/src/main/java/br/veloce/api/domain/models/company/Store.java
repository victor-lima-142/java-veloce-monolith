package br.veloce.api.domain.models.company;

import br.veloce.api.domain.enums.UserRole;
import br.veloce.api.domain.models.embeddable.Address;
import br.veloce.api.domain.models.embeddable.UserStoreId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stores")
@SQLDelete(sql = "UPDATE stores SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column
    private String document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserStore> users = new HashSet<>();

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

    public void addUSer(User user) {
        UserStore userStore = UserStore.builder()
                .id(new UserStoreId(user.getId(), this.getId()))
                .user(user)
                .store(this)
                .build();
        user.getStores().add(userStore);
        this.getUsers().add(userStore);
    }

    public void addUSer(User user, UserRole role) {
        UserStore userStore = UserStore.builder()
                .id(new UserStoreId(user.getId(), this.getId()))
                .user(user)
                .store(this)
                .roleOverride(role)
                .build();
        user.getStores().add(userStore);
        this.getUsers().add(userStore);
    }

    public void removeUser(User user) {
        this.getUsers().removeIf(u -> Objects.equals(u.getUser().getId(), user.getId()));
        user.getStores().removeIf(s -> Objects.equals(s.getStore().getId(), this.getId()));
    }
}
