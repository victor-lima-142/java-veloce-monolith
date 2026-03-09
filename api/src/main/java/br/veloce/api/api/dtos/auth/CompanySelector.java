package br.veloce.api.api.dtos.auth;

import br.veloce.api.domain.models.company.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanySelector {
    @JsonProperty(value = "company_id", required = true)
    private String companyId;

    @JsonProperty(value = "company_name", required = true)
    private String companyName;

    @JsonProperty(value = "company_document", required = true)
    private String companyDocument;

    public CompanySelector(Company company) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyDocument = company.getDocument();
    }
}
