package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperation();
    }

    @Test
    void apply_newFruit_addsQuantity() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "apple", 50);
        int result = handler.apply(0, transaction.getQuantity());
        assertEquals(50, result);
    }

    @Test
    void apply_existingFruit_addsToExisting() {
        int result = handler.apply(100, 50);
        assertEquals(150, result);
    }

    @Test
    void apply_zeroQuantity_noChange() {
        int result = handler.apply(100, 0);
        assertEquals(100, result);
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        int currentQuantity = 100;
        int transactionQuantity = -10;
        assertThrows(RuntimeException.class, () ->
                handler.apply(currentQuantity, transactionQuantity));
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
