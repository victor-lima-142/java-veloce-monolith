package br.veloce.api.api.dtos.vehicle;

import br.veloce.api.domain.enums.motorcycle.MotoEngineType;
import br.veloce.api.domain.enums.motorcycle.MotoFuelType;
import br.veloce.api.domain.enums.motorcycle.MotoTransmissionType;
import br.veloce.api.domain.enums.motorcycle.MotorcycleBodyType;
import br.veloce.api.domain.models.vehicle.MotoSpecification;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoSpecSummary {
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "body_type", required = true)
    private MotorcycleBodyType bodyType;

    @JsonProperty(value = "vehicle_id", required = true)
    private String vehicleId;

    @JsonProperty(value = "fuel", required = true)
    private MotoFuelType fuel;

    @JsonProperty(value = "engine", required = true)
    private MotoEngineType engine;

    @JsonProperty(value = "transmission", required = true)
    private MotoTransmissionType transmission;

    @JsonProperty(value = "engine_cc", required = true)
    private Integer engineCc;

    @JsonProperty(value = "false", required = true)
    private Boolean hasSidecar = false;

    public MotoSpecSummary(MotoSpecification motoSpecification) {
        this.id = motoSpecification.getId();
        this.vehicleId = motoSpecification.getVehicle().getId();
        this.fuel = motoSpecification.getFuel();
        this.engine = motoSpecification.getEngine();
        this.transmission = motoSpecification.getTransmission();
        this.bodyType = motoSpecification.getBodyType();
        this.engineCc = motoSpecification.getEngineCc();
        this.hasSidecar = motoSpecification.getHasSidecar();
    }
}
