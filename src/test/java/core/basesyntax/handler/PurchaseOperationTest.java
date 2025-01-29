package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchase;

    @BeforeAll
    static void beforeAll() {
        purchase = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void purchase_enoughFruit_ok() {
        Storage.storage.put("peach", 10);
        purchase.handle(Storage.storage,
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "peach", 5));
        assertEquals(5, Storage.storage.get("peach"));
    }

    @Test
    void purchase_notEnough_notOk() {
        assertThrows(IllegalArgumentException.class,() ->
                purchase.handle(Storage.storage,
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "peach", 5)));
    }
}
