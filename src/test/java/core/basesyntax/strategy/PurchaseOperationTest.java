package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.PurchaseOperation;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation = new PurchaseOperation();

    @BeforeAll
    static void beforeAll() {
        Storage.fruits.put("Banana", 10);
    }

    @Test
    void purchaseOperation_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 5);
        purchaseOperation.processOperation(fruitTransaction);
        assertEquals(5, Storage.fruits.get("Banana"));
    }

    @Test
    void purchaseOperation_MoreQuantityThanAvailableInStorage_NotOk() {
        Storage.fruits.clear();
        Storage.fruits.put("Banana", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 15);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseOperation.processOperation(fruitTransaction);
        });

        assertEquals("Unable to do operation(not enough fruits)", exception.getMessage());
        assertEquals(10, Storage.fruits.get("Banana"));
    }
}
