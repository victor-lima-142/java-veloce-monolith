package br.veloce.api.api.dtos;

import br.veloce.api.domain.enums.CardFlag;
import br.veloce.api.domain.models.embeddable.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    @JsonProperty(value = "last_four_digits", required = true)
    private String lastFourDigits;

    @JsonProperty(value = "cvv", required = true)
    private String cvv;

    @JsonProperty(value = "exp_month", required = true)
    private Integer expMonth;

    @JsonProperty(value = "exp_year", required = true)
    private Integer expYear;

    @JsonProperty(value = "holder_name", required = true)
    private String holderName;

    @JsonProperty(value = "flag", required = true)
    private CardFlag flag;

    public CardDTO(Card card) {
        this.lastFourDigits = card.getLastFourDigits();
        this.cvv = card.getCvv();
        this.expMonth = card.getExpMonth();
        this.expYear = card.getExpYear();
        this.holderName = card.getHolderName();
        this.flag = card.getFlag();
    }
}
