package br.veloce.api.api.dtos.vehicle;

import br.veloce.api.api.dtos.company.UserBasic;
import br.veloce.api.domain.enums.vehicle.VehicleColor;
import br.veloce.api.domain.enums.vehicle.VehicleType;
import br.veloce.api.domain.models.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleSummary {

    private static final Logger log = LoggerFactory.getLogger(VehicleSummary.class);
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "type", required = true)
    private VehicleType type;

    @JsonProperty(value = "color", required = true)
    private VehicleColor color;

    @JsonProperty(value = "year_manufacture", required = true)
    private Integer yearManufacture;

    @JsonProperty(value = "year_model", required = true)
    private Integer yearModel;

    @JsonProperty(value = "mileage", required = true)
    private Integer mileage;

    @JsonProperty(value = "market_value", required = true)
    private Long marketValue;

    @JsonProperty(value = "value", required = true)
    private Long value;

    @JsonProperty(value = "manufacturer_name", required = true)
    private String manufacturerName;

    @JsonProperty(value = "inventory_code", required = true)
    private String inventoryCode;

    @JsonProperty(value = "inventory_item_id", required = true)
    private String inventoryItemId;

    @JsonProperty(value = "model", required = true)
    private String model;

    @JsonProperty(value = "car")
    private CarSpecSummary carSpecSummary;

    @JsonProperty(value = "moto")
    private MotoSpecSummary motoSpecSummary;

    @JsonProperty(value = "user_creator")
    private UserBasic userCreator;

    public VehicleSummary(Vehicle vehicle) {
        try {
            if (vehicle.getType() == VehicleType.CAR && vehicle.getCarSpecification() != null) {
                this.carSpecSummary = new CarSpecSummary(vehicle.getCarSpecification());
            } else if (vehicle.getMotoSpecification() != null){
                this.motoSpecSummary = new MotoSpecSummary(vehicle.getMotoSpecification());
            }

            this.id = vehicle.getId();
            this.model = vehicle.getModel();
            this.type = vehicle.getType();
            this.color = vehicle.getColor();
            this.yearManufacture = vehicle.getYearManufacture();
            this.yearModel = vehicle.getYearModel();
            this.mileage = vehicle.getMileage();
            this.marketValue = vehicle.getMarketValue();
            this.value = vehicle.getValue();
            this.manufacturerName = vehicle.getManufacturer().getName();
            this.inventoryCode = vehicle.getInventoryItem().getInventoryCode();
            this.inventoryItemId = vehicle.getInventoryItem().getId();
            if (vehicle.getCreatedUser() != null) {
                this.userCreator = new UserBasic(vehicle.getCreatedUser());
            }
            this.name = String.format("%s %s - %s",
                    getManufacturerName(),
                    getModel(),
                    getYearModel());
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message);
        }
    }
}
