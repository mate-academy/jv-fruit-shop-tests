package core.basesyntax.handlertest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.DATABASE.clear();
    }

    @Test
    void handleTransaction_valid_ok() {
        Storage.DATABASE.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 30);
        assertDoesNotThrow(() -> purchaseOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("apple");
        assertEquals(70, actual);
    }

    @Test
    void handleTransaction_notEnoughQuantity_notOk() {
        Storage.DATABASE.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 101);
        assertThrows(InvalidDataException.class,
                () -> purchaseOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("apple");
        assertEquals(100, actual);
    }
}
