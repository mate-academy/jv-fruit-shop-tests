package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler handler = new BalanceOperationHandler();

    @BeforeEach
    public void setUp() {
        Storage.clear();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void handle_validTransaction_setsQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);

        handler.handle(transaction);

        assertEquals(20, Storage.getQuantity("banana"));
    }

    @Test
    public void handle_existingFruit_replacesQuantity() {
        Storage.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);

        handler.handle(transaction);

        assertEquals(20, Storage.getQuantity("banana"));
    }
}
