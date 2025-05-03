package core.basesyntax.strategy.handler;

import core.basesyntax.model.FruitTransaction;

public class BalancesHandlerImpl implements Handler {
    private Handler addOnHandlers = new AddingHandlers();

    public void handler(FruitTransaction fruitTransaction) {
        addOnHandlers.handler(fruitTransaction);
    }
}
