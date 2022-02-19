package core.basesyntax.strategy;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class PurchaseActivity implements TypeOfActivity {
    @Override
    public void realizeType(FruitTransaction fruitTransaction) {
        Fruit fruit = new Fruit(fruitTransaction.getFruit());
        int currentAmount = Storage.storage.get(fruit);
        checkValidData(fruit,currentAmount);
        Storage.storage.put(fruit,currentAmount - fruitTransaction.getQuantity());
    }
}
