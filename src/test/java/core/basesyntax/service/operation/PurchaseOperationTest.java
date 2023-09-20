package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void purchaseOperation_validData_ok() {
        Storage.STORAGE.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.PURCHASE, "banana", 40);
        purchaseOperation.operate(fruitTransaction);
        int actual = Storage.STORAGE.get("banana");
        assertEquals(60, actual);
    }

    @Test
    void operate_fruitQuantityNotEnough_notOk() {
        Storage.STORAGE.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.PURCHASE, "banana", 140);
        assertThrows(RuntimeException.class, () -> purchaseOperation.operate(fruitTransaction));
    }
}
