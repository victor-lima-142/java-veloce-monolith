package br.veloce.api.domain.models.embeddable;

import br.veloce.api.domain.enums.CardFlag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @JsonIgnore
    @Column(name = "card_number", nullable = false, length = 19)
    private String cardNumber;

    @Column(name = "last_four_digits", nullable = false, length = 4)
    private String lastFourDigits;

    @JsonIgnore
    @Column(name = "cvv", nullable = false, length = 4)
    private String cvv;

    @Column(name = "exp_month", nullable = false)
    private Integer expMonth;

    @Column(name = "exp_year", nullable = false)
    private Integer expYear;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag", nullable = false, length = 20)
    private CardFlag flag;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        if (cardNumber != null) {
            String digits = cardNumber.replaceAll("\\D", "");
            this.lastFourDigits = digits.substring(digits.length() - 4);
        }
    }

    public static class CardBuilder {
        public CardBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            if (cardNumber != null) {
                String digits = cardNumber.replaceAll("\\D", "");
                this.lastFourDigits = digits.substring(digits.length() - 4);
            }
            return this;
        }
    }
}
