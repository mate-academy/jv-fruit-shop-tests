package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitShop;

public class ReturnOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction transaction) {
        if (!FruitShop.storage.containsKey(transaction.getFruit())) {
            throw new RuntimeException("Shop doesnt contain fruit: " + transaction.getFruit());
        }

        int current = FruitShop.storage.get(transaction.getFruit());
        FruitShop.storage.put(transaction.getFruit(), current + transaction.getQuantity());
    }
}
