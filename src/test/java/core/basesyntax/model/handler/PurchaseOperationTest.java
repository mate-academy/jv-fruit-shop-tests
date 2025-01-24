package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void purchaseNullFruitTransactionNotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(null));
    }

    @Test
    void purchaseMaxIntMoreThanHaveNotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "kiwi", Integer.MAX_VALUE)));
    }

    @Test
    void purchaseNullOperationNotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                new FruitTransaction(null,
                        "apple", 10)));
    }

    @Test
    void purchaseNullFruitNotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null, 10)));
    }

    @Test
    void purchaseIncorrectOperationNotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "kiwi", 10)));
    }

    @Test
    void purchaseOk() {
        Storage.getStorage().put("banana", 100);
        purchaseOperation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 33));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 67);
        assertTrue(Storage.getStorage().entrySet().contains(expectedEntry));
    }
}
