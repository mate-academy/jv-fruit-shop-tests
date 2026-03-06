package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitTransaction fruitTransaction;
    private PurchaseOperation purchaseOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getFruitStorage();
        Storage.clear();
        Storage.put("apple", 10);
        purchaseOperation = new PurchaseOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void performOperation_validData_ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation("p");
        fruitTransaction.setQuantity(10);
        purchaseOperation.performOperation(fruitTransaction);
        int expected = 0;
        int actual = storage.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_invalidData_throwsException() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation("p");
        fruitTransaction.setQuantity(20);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.performOperation(fruitTransaction));
    }
}
