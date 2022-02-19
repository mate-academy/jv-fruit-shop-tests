package core.basesyntax.strategy;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class SupplyActivity implements TypeOfActivity {
    @Override
    public void realizeType(FruitTransaction fruitTransaction) {
        Fruit fruit = new Fruit(fruitTransaction.getFruit());
        if (!Storage.storage.containsKey(fruit)) {
            Storage.storage.put(fruit, fruitTransaction.getQuantity());
        } else {
            int oldAmount = Storage.storage.get(fruit);
            Storage.storage.put(fruit, oldAmount + fruitTransaction.getQuantity());
        }
    }
}
