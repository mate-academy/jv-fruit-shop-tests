package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private OperationHandler balanceOperation;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    public void tearDown() {
        Storage.getAllFruits().keySet().forEach(fruit -> Storage.updateQuantity(fruit, 0));
    }

    @Test
    public void handle_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "banana", 50);
        balanceOperation.handle(transaction);

        int actualQuantity = Storage.getQuantity("banana");
        assertEquals(50, actualQuantity);
    }
}


