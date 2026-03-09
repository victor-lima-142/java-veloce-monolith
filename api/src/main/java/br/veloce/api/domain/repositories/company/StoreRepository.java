package br.veloce.api.domain.repositories.company;

import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.company.UserStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    @Query("""
                SELECT s FROM Store s
                JOIN FETCH s.company
                WHERE s.company = :company
            """)
    Page<Store> findByCompany(Company company, Pageable pageable);

    @Query("""
                SELECT us FROM UserStore us
                JOIN FETCH us.user
                WHERE us.store = :store
            """)
    Page<UserStore> findUsersOfStore(Store store, Pageable pageable);

    @Query("SELECT s FROM Store s JOIN FETCH s.company WHERE s.id = :storeId")
    Optional<Store> findByIdWithCompany(String storeId);
}
