package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseOperationHandler();
        transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"Apple", 5);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void handle_PositiveQuantity_Ok() {
        Storage.fruitStorage.put("Apple", 10);
        transaction.setQuantity(5);
        purchaseHandler.handle(transaction);
        assertEquals(5, Storage.fruitStorage.get("Apple"));
    }

    @Test
    public void handle_ZeroQuantity_notOk() {
        Storage.fruitStorage.put("Banana", 10);
        transaction.setQuantity(0);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
    }

    @Test
    public void handle_NegativeQuantity_notOk() {
        Storage.fruitStorage.put("Banana", 10);
        transaction.setQuantity(-5);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
    }
}
