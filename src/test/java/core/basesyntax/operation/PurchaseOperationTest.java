package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final OperationHandler purchaseOperation = new PurchaseOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 100);
    }

    @Test
    void processOperation_correctData_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 15);
        Map<String, Integer> expected = Map.of(
                fruitTransaction.getFruit(),
                Storage.storage.get("banana") - fruitTransaction.getAmount(),
                "apple", 100);
        purchaseOperation.processOperation(fruitTransaction);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void processOperation_notEnoughInStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 25);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.processOperation(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
