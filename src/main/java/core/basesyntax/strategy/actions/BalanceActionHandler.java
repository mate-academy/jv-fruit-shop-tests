package core.basesyntax.strategy.actions;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;

public class BalanceActionHandler implements ActionHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("How come we are in debt?");
        }
        Storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
