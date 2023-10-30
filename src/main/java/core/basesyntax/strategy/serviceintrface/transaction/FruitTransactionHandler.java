package core.basesyntax.strategy.serviceintrface.transaction;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionHandler {
    void processTransactionsList(List<FruitTransaction> fruitTransactionList);
}

