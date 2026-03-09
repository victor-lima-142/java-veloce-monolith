package br.veloce.api.domain.repositories.company;

import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.company.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = """
        SELECT c.* FROM users u
        JOIN companies c ON u.company_id = c.id
        WHERE u.id = :userId
    """, nativeQuery = true)
    Collection<Company> findCompaniesOfUser(String userId);

    @Query(value = """
        SELECT st.* FROM users u
        JOIN user_stores us ON u.id = us.user_id
        JOIN stores st ON us.store_id = st.id
        WHERE u.id = :userId
    """, nativeQuery = true)
    Collection<Store> findStoresOfUser(String userId);
}
