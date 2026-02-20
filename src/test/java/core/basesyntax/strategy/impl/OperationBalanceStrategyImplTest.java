package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Test;

class OperationBalanceStrategyImplTest {
    private OperationStrategy operationStrategy = new OperationBalanceStrategyImpl();
    private FruitTransaction fruitTransaction;

    @Test
    void applyFruitTransaction_ShouldAddTransactionToStorage() {
        Storage.getStorage().clear();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        Integer expectedQuantity = 5;
        fruitTransaction.setQuantity(5);

        operationStrategy.apply(fruitTransaction);

        assertEquals(expectedQuantity, Storage.getStorage().get("banana"));
    }
}
