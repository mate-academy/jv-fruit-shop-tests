package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Test;

class OperationSupplyStrategyImplTest {
    private OperationStrategy operationStrategy = new OperationSupplyStrategyImpl();
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
