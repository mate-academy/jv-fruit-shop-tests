package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.models.FruitTransaction;

public class ShopReturn implements ShopActivities {
    @Override
    public void updateStorageData(FruitTransaction transaction) {
        minusValueException(transaction);
        if (FruitStorage.fruits.containsKey(transaction.getFruitType())) {
            int oldAmount = FruitStorage.fruits.get(transaction.getFruitType());
            int newAmount = oldAmount + transaction.getFruitAmount();
            FruitStorage.fruits.put(transaction.getFruitType(), newAmount);
        } else {
            throw new RuntimeException("We don't have such type of Fruit before to return: "
                    + transaction.getFruitType());
        }
    }
}
