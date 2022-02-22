package core.basesyntax.strategy;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class BalanceActivity implements TypeOfActivity {
    @Override
    public void realizeType(FruitTransaction fruitTransaction) {
        Fruit fruit = new Fruit(fruitTransaction.getFruit());
        checkValidData(fruit, fruitTransaction.getQuantity());
        Storage.storage.put(fruit, fruitTransaction.getQuantity());
    }

    @Override
    public void checkValidData(Fruit fruit, int currentAmount) {
        TypeOfActivity.super.checkValidData(fruit, currentAmount);
    }
}
