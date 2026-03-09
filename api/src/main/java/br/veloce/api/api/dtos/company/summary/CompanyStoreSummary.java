package br.veloce.api.api.dtos.company.summary;

import br.veloce.api.api.dtos.AddressDTO;
import br.veloce.api.domain.models.company.Store;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyStoreSummary {
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "company_id", required = true)
    private String companyId;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "phone", required = true)
    private String phone;

    @JsonProperty(value = "document", required = true)
    private String document;

    @JsonProperty(value = "address", required = true)
    private AddressDTO address;

    public CompanyStoreSummary(Store store) {
        this.setCompanyId(store.getCompany().getId());
        this.setId(store.getId());
        this.setName(store.getName());
        this.setPhone(store.getPhone());
        this.setDocument(store.getDocument());
        this.setAddress(new AddressDTO(store.getAddress()));
    }
}
