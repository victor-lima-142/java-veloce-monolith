package br.veloce.api.api.dtos.auth;

import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.models.company.Store;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreSelector {
    @JsonProperty(value = "store_id", required = true)
    private String storeId;

    @JsonProperty(value = "store_name", required = true)
    private String storeName;

    @JsonProperty(value = "store_document", required = true)
    private String storeDocument;

    public StoreSelector(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.storeDocument = store.getDocument();
    }
}
