package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static OperationHandler balanceHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        balanceHandler = new ReturnHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"Apple", 10);
    }

    @Test
    void handle_positiveQuantity_Ok() {
        Storage.storage.put("Apple", 10);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Apple", 20);
        balanceHandler.handle(fruitTransaction);
        assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    void handle_zeroQuantity_notOk() {
        fruitTransaction.setQuantity(0);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }
}
