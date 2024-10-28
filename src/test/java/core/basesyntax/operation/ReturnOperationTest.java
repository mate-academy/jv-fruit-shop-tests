package core.basesyntax.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        FruitStorage.getFruits().put("apple", 20);
        FruitStorage.getFruits().put("banana", 10);
    }

    @Test
    public void transaction_existingFruit_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN, "apple", 10);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 30;
        int actualQuantity = FruitStorage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_zeroQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN, "banana", 0);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 10;
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
