package core.basesyntax.strategy.handler;

import core.basesyntax.model.FruitTransaction;

public class SupplyHandlerImpl implements Handler {
    private Handler addInStorage = new AddingHandlers();

    @Override
    public void handler(FruitTransaction fruitTransaction) {
        addInStorage.handler(fruitTransaction);
    }
}


