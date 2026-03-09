package br.veloce.api.api.dtos.company;

import br.veloce.api.api.dtos.AddressDTO;
import br.veloce.api.domain.enums.UserRole;
import br.veloce.api.domain.models.company.User;
import br.veloce.api.domain.models.company.UserStore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBasic {

    @JsonProperty(required = true)
    private String id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private UserRole role = UserRole.SALER;

    @JsonProperty
    private AddressDTO address;

    public UserBasic(UserStore userStore) {
        this.id = userStore.getUser().getId();
        this.role = userStore.getRoleOverride() == null ? userStore.getUser().getRole() : userStore.getRoleOverride();
        this.name = userStore.getUser().getName();
        this.email = userStore.getUser().getEmail();
        this.address = new AddressDTO(userStore.getUser().getAddress());
    }

    public UserBasic(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = new AddressDTO(user.getAddress());
    }
}
