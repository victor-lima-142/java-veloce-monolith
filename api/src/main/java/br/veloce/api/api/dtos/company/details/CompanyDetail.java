package br.veloce.api.api.dtos.company.details;

import br.veloce.api.api.dtos.AddressDTO;
import br.veloce.api.api.dtos.CardDTO;
import br.veloce.api.api.dtos.PagedResponse;
import br.veloce.api.api.dtos.company.summary.CompanyStoreSummary;
import br.veloce.api.api.dtos.company.summary.CompanySummary;
import br.veloce.api.domain.models.company.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetail extends CompanySummary {
    @JsonProperty(value = "stores", required = true)
    private PagedResponse<CompanyStoreSummary> stores;

    @JsonProperty(value = "address", required = true)
    private AddressDTO address;

    @JsonProperty(value = "card", required = true)
    private CardDTO card;

    public CompanyDetail(Company company, PagedResponse<CompanyStoreSummary> stores) {
        super(company);
        this.address = new AddressDTO(company.getAddress());
        this.card = new CardDTO(company.getCard());
        this.stores = stores;
    }

    public CompanyDetail(Company company, Page<CompanyStoreSummary> stores) {
        super(company);
        this.address = new AddressDTO(company.getAddress());
        this.card = new CardDTO(company.getCard());
        this.stores = new PagedResponse<CompanyStoreSummary>(stores);
    }
}
