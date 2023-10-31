package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private SupplyHandler supplyHandler;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyHandler();
        storage = new HashMap<>();
    }

    @Test
    void testHandleTransaction() {
        String fruit = "apple";
        int quantity = 10;

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit,
                quantity);
        supplyHandler.handleTransaction(transaction, storage);

        assertEquals(quantity, storage.getOrDefault(fruit, 0));
    }

    @Test
    void testHandleTransactionForExistingFruit() {
        String fruit = "banana";
        int intialQuantity = 5;
        int supllyQuantity = 10;

        storage.put(fruit, intialQuantity);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit,
                supllyQuantity);
        supplyHandler.handleTransaction(transaction, storage);

        assertEquals(intialQuantity + supllyQuantity, storage.getOrDefault(fruit, 0));
    }
}
