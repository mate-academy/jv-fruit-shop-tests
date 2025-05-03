package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple", 1);
    }

    @Test
    public void calculate_validTransaction_ok() {
        Storage.fruitInventory.put("apple", 10);
        purchaseHandler.calculate(transaction);
        int actualQuantity = Storage.fruitInventory.get(transaction.getFruit());
        int expectedQuantity = 9;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void calculate_leedsToNegativeBalance_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                purchaseHandler.calculate(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitInventory.clear();
    }
}
