package br.veloce.api.domain.repositories.company;

import br.veloce.api.domain.models.company.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserStoreRepository extends JpaRepository<UserStore, String> {
    @Query("SELECT us FROM UserStore us WHERE us.user.id = :userId AND us.store.id = :storeId")
    Optional<UserStore> findByUserIdAndStoreId(String userId, String storeId);
}
