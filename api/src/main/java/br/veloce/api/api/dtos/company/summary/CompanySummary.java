package br.veloce.api.api.dtos.company.summary;

import br.veloce.api.domain.models.company.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySummary {
    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "document", required = true)
    private String document;

    @JsonProperty(value = "website_url")
    private String websiteUrl;

    public CompanySummary(Company company) {
        this.setId(company.getId());
        this.setName(company.getName());
        this.setWebsiteUrl(company.getWebsiteUrl());
        this.setDocument(company.getDocument());
    }
}
