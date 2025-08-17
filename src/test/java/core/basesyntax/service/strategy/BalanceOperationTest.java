package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperation();
    }

    @Test
    void apply_newFruit_setsBalance() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 100);

        int result = handler.apply(0, transaction.getQuantity());

        assertEquals(100, result);
    }

    @Test
    void apply_existingFruit_replacesBalance() {
        int result = handler.apply(50, 100);

        assertEquals(100, result);
    }

    @Test
    void apply_zeroQuantity_setsZero() {
        int result = handler.apply(50, 0);

        assertEquals(0, result);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
