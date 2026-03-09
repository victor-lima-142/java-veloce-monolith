package br.veloce.api.api.dtos;

import br.veloce.api.domain.models.embeddable.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @JsonProperty(required = true)
    private String country;

    @JsonProperty(required = true)
    private String state;

    @JsonProperty(required = true)
    private String city;

    @JsonProperty(required = true)
    private String district;

    @JsonProperty(required = true)
    private String street;

    @JsonProperty(required = true)
    private String number;

    @JsonProperty(required = true)
    private String complement;

    public AddressDTO(Address address) {
        this.city = address.getCity();
        this.country = address.getCountry();
        this.state = address.getState();
        this.district = address.getDistrict();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
    }
}
