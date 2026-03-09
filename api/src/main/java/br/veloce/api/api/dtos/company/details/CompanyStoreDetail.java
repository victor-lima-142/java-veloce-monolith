package br.veloce.api.api.dtos.company.details;

import br.veloce.api.api.dtos.PagedResponse;
import br.veloce.api.api.dtos.company.UserBasic;
import br.veloce.api.api.dtos.company.summary.CompanyStoreSummary;
import br.veloce.api.domain.models.company.Store;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class CompanyStoreDetail extends CompanyStoreSummary {
    public CompanyStoreDetail(Store store) {
        super(store);
    }
}
