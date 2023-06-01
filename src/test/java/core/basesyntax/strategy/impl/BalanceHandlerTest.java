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

public class BalanceHandlerTest {

    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    void handle_positiveQuantity_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"Apple", 10);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Apple", 10);
        balanceHandler.handle(fruitTransaction);
        assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    void handle_zeroQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"Apple", 0);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"Apple", -5);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }
}
