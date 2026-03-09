package br.veloce.api.domain.models.company;

import br.veloce.api.domain.enums.SubscriptionPlan;
import br.veloce.api.domain.models.embeddable.Address;
import br.veloce.api.domain.models.embeddable.Card;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
@SQLDelete(sql = "UPDATE companies SET deleted_at = NOW() WHERE id = ?")
@SQLSelect(sql = "deleted_at IS NULL")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(nullable = false)
    private String document;

    @Builder.Default
    @Column(nullable = false, name = "subscription_plan")
    private SubscriptionPlan subscriptionPlan = SubscriptionPlan.FREE;

    @Builder.Default
    @Column(nullable = false)
    private OffsetDateTime trialEndsAt = OffsetDateTime.now().plusDays(30).with(LocalTime.MAX);

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default
    @Column(nullable = false, name = "discount")
    private long discount = 0L;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @Builder.Default
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt = null;

    @Embedded
    private Address address;

    @Embedded
    private Card card;

    public void addStore(Store store) {
        this.stores.add(store);
        store.setCompany(this);
    }

    public void removeStore(Store store) {
        this.stores.remove(store);
        store.setCompany(null);
        store.setDeletedAt(OffsetDateTime.now());
    }
}
