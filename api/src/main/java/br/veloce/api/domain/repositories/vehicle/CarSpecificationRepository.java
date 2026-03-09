package br.veloce.api.domain.repositories.vehicle;

import br.veloce.api.domain.models.vehicle.CarSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSpecificationRepository extends JpaRepository<CarSpecification, String> {
}
