package br.veloce.api.domain.models.company;

import br.veloce.api.domain.enums.UserRole;
import br.veloce.api.domain.models.embeddable.Address;
import br.veloce.api.domain.models.embeddable.UserStoreId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private UserRole role = UserRole.SALER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserStore> stores = new HashSet<>();

    @Embedded
    private Address address;

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

    public void addStore(Store store) {
        UserStore userStore = UserStore.builder()
                .id(new UserStoreId(this.getId(), store.getId()))
                .user(this)
                .store(store)
                .build();
        this.getStores().add(userStore);
        store.getUsers().add(userStore);
    }

    public void addStore(Store store, UserRole role) {
        UserStore userStore = UserStore.builder()
                .id(new UserStoreId(this.getId(), store.getId()))
                .user(this)
                .store(store)
                .roleOverride(role)
                .build();
        this.getStores().add(userStore);
        store.getUsers().add(userStore);
    }

    public void removeStore(Store store) {
        this.getStores().removeIf(u -> Objects.equals(u.getStore().getId(), store.getId()));
        store.getUsers().removeIf(s -> Objects.equals(s.getUser().getId(), this.getId()));
    }

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @NullMarked
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
