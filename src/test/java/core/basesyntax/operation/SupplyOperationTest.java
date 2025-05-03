package core.basesyntax.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
        FruitStorage.getFruits().put("apple", 10);
        FruitStorage.getFruits().put("banana", 20);
    }

    @Test
    public void transaction_increaseQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, APPLE, 15);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 25;
        int actualQuantity = FruitStorage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_zeroQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, BANANA, 0);
        operationHandler.processOperation(fruitTransaction);
        int expectedQuantity = 20;
        int actualQuantity = FruitStorage.getFruits().get("banana");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_nullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = null;
        Assertions.assertThrows(NullPointerException.class, () ->
                operationHandler.processOperation(fruitTransaction));
    }
}
