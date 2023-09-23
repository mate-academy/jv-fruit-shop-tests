package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.PurchaceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaceOperationTest {
    private OperationService purchaseOperation = new PurchaceOperation();

    @Test
    void processPurchaseOperation_quantityLessThanAmount_notOk() {
        Storage.getStorage().put("apple", 15);
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.PURCHASE, "apple", 30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.doOperation(fruitTransaction));
    }

    @Test
    void processPurchaseOperation_ok() {
        Storage.getStorage().put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.PURCHASE, "apple", 30);
        assertDoesNotThrow(() -> purchaseOperation.doOperation(fruitTransaction));
        int actual = Storage.getStorage().get("apple");
        assertEquals(20, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
