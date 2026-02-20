package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Test;

class OperationPurchareStrategyImplTest {
    private OperationStrategy operationStrategy = new OperationPurchareStrategyImpl();
    private FruitTransaction fruitTransaction;

    @Test
    void applyFruitTransactionToEmptyStorage_ShouldAddTransactionToStorage() {
        int expectedQuantity = 5;
        Storage.getStorage().clear();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(5);

        assertThrows(RuntimeException.class, () -> operationStrategy.apply(fruitTransaction));
    }

    @Test
    void applyValidTransactionToStorage_ShouldMinusQuantity() {
        Storage.getStorage().clear();
        Storage.getStorage().put("banana", 10);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        Integer expectedQuantity = 5;
        fruitTransaction.setQuantity(5);

        operationStrategy.apply(fruitTransaction);
        assertEquals(expectedQuantity, Storage.getStorage().get("banana"));
    }
}
