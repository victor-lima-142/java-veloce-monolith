package br.veloce.api.domain.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserStoreId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "store_id")
    private String storeId;
}
