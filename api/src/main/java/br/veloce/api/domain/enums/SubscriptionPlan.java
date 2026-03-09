package br.veloce.api.domain.enums;

import lombok.Getter;

@Getter
public enum SubscriptionPlan {
    FREE(0),
    STARTER(1500L),
    PRO(2500L),
    TEAM(3600L);

    private final long amountInCents;

    SubscriptionPlan(long amountInCents) {
        this.amountInCents = amountInCents;
    }

    public long calculateTotalWithDiscount(long users) {
        long baseTotal = users * amountInCents;
        long discountPercentage = getDiscountPercentage(users);
        return (baseTotal * discountPercentage) / 100;
    }

    private long getDiscountPercentage(long users) {
        if (users > 200) return 80L;
        if (users > 100) return 85L;
        if (users > 50) return 90L;
        return 100L;
    }
}