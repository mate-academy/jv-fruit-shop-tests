package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.models.FruitTransaction;

public class ShopBalance implements ShopActivities {
    @Override
    public void updateStorageData(FruitTransaction transaction) {
        minusValueException(transaction);
        if (!FruitStorage.fruits.containsKey(transaction.getFruitType())) {
            FruitStorage.fruits.put(transaction.getFruitType(), transaction.getFruitAmount());
        } else {
            throw new RuntimeException("We can't balance our fruits. "
                    + "This operation can only be called once.");
        }
    }
}
