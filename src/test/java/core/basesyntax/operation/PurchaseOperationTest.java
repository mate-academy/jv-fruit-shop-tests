package core.basesyntax.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static OperationHandler operationHandler;

    @BeforeEach
    void beforeEach() {
        operationHandler = new PurchaseOperation();
        FruitStorage.getFruits().put("apple", 40);
        FruitStorage.getFruits().put("banana", 20);
    }

    @Test
    public void transaction_decreaseQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE,"apple", 10);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 30;
        int actualQuantity = FruitStorage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_zeroQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE,"banana", 20);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 0;
        int actualQuantity = FruitStorage.getFruits().get("banana");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_fruitNotInTheStorage_notOk() {
        FruitTransaction fruitTransaction = null;
        Assertions.assertThrows(NullPointerException.class, () ->
                operationHandler.processOperation(fruitTransaction));
    }
}
