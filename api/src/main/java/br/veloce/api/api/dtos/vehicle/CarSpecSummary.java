package br.veloce.api.api.dtos.vehicle;

import br.veloce.api.domain.enums.car.CarBodyType;
import br.veloce.api.domain.enums.car.CarEngineType;
import br.veloce.api.domain.enums.car.CarFuelType;
import br.veloce.api.domain.enums.car.CarTransmissionType;
import br.veloce.api.domain.models.vehicle.CarSpecification;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSpecSummary {
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "vehicle_id", required = true)
    private String vehicleId;

    @JsonProperty(value = "doors", required = true)
    private Integer doors;

    @JsonProperty(value = "fuel", required = true)
    private CarFuelType fuel;

    @JsonProperty(value = "engine", required = true)
    private CarEngineType engine;

    @JsonProperty(value = "transmission", required = true)
    private CarTransmissionType transmission;

    @JsonProperty(value = "body_type", required = true)
    private CarBodyType bodyType = CarBodyType.OTHER;

    public CarSpecSummary(CarSpecification carSpecification) {
        this.id = carSpecification.getId();
        this.vehicleId = carSpecification.getVehicle().getId();
        this.doors = carSpecification.getDoors();
        this.fuel = carSpecification.getFuel();
        this.engine = carSpecification.getEngine();
        this.transmission = carSpecification.getTransmission();
        this.bodyType = carSpecification.getBodyType();
    }
}

