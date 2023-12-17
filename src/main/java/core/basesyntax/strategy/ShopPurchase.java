package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.models.FruitTransaction;

public class ShopPurchase implements ShopActivities {
    @Override
    public void updateStorageData(FruitTransaction transaction) {
        minusValueException(transaction);
        if (FruitStorage.fruits.containsKey(transaction.getFruitType())) {
            int amount;
            if (transaction.getFruitAmount()
                    < (amount = FruitStorage.fruits.get(transaction.getFruitType()))) {
                int newAmount = amount - transaction.getFruitAmount();
                FruitStorage.fruits.put(transaction.getFruitType(), newAmount);
            } else {
                FruitStorage.fruits.put(transaction.getFruitType(), 0);
            }
        } else {
            throw new RuntimeException("We don't have this Fruit Type: "
                    + transaction.getFruitType());
        }
    }
}
