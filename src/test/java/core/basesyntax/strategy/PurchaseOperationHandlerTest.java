package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler handler = new PurchaseOperationHandler();

    @BeforeEach
    public void setUp() {
        Storage.clear();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void handle_enoughFruit_decreasesQuantity() {
        Storage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 7);

        handler.handle(transaction);

        assertEquals(13, Storage.getQuantity("banana"));
    }

    @Test
    public void handle_exactQuantity_setsZero() {
        Storage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);

        handler.handle(transaction);

        assertEquals(0, Storage.getQuantity("banana"));
    }

    @Test
    public void handle_notEnoughFruit_notOk() {
        Storage.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 6);

        assertThrows(RuntimeException.class, () -> handler.handle(transaction));
        assertEquals(5, Storage.getQuantity("banana"));
    }
}
