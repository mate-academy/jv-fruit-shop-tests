package core.basesyntax.strategy;

import static core.basesyntax.storage.Storage.dataBase;

import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        dataBase.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
