package core.basesyntax.service.handler;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;
    private Storage storage;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        storage = new Storage();

        Map<String, Integer> initialFruits = new HashMap<>();
        initialFruits.put("apple", 10);
        initialFruits.put("banana", 5);
        storage.setFruits(initialFruits);
    }

    @Test
    public void transaction_validTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 15);

        operationHandler.transaction(fruitTransaction, storage);

        int expectedQuantity = 15;
        int actualQuantity = storage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_zeroQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 0);

        operationHandler.transaction(fruitTransaction, storage);

        int expectedQuantity = 0;
        int actualQuantity = storage.getFruits().get("banana");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_nullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = null;

        Assertions.assertThrows(NullPointerException.class, () ->
                operationHandler.transaction(fruitTransaction, storage));
    }
}
