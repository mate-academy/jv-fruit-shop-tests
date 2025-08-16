package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private Map<String, Integer> storage;
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        storage.put("banana", 50);
        handler = new ReturnHandler();
    }

    @Test
    void applyReturn_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        handler.apply(fruitTransaction, storage);
        assertEquals(60, storage.get("banana"));
    }

    @Test
    void applyReturnNegativeQuantity_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -10);
        assertThrows(IllegalArgumentException.class, () -> {
            handler.apply(fruitTransaction, storage);
        });
    }
}
