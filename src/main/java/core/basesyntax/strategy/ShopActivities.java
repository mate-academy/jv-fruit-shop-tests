package core.basesyntax.strategy;

import core.basesyntax.models.FruitTransaction;

public interface ShopActivities {
    void updateStorageData(FruitTransaction transaction);

    default void minusValueException(FruitTransaction transaction) {
        if (transaction.getFruitAmount() <= 0) {
            throw new RuntimeException("It is not possible to operate"
                    + " with a negative value or Zero: " + transaction.getFruitAmount());
        }
    }
}
