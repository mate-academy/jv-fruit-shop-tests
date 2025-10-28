package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.BalanceOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {

    private BalanceOperationHandler handler;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        storage = new HashMap<>();
    }

    @Test
    void apply_newFruit_setsInitialQuantity() {
        FruitTransaction tx = new FruitTransaction(Operation.BALANCE, "banana", 100);
        handler.apply(tx, storage);
        assertEquals(100, storage.get("banana"));
    }

    @Test
    void apply_existingFruit_overwritesQuantity() {
        storage.put("banana", 50);
        FruitTransaction tx = new FruitTransaction(Operation.BALANCE, "banana", 200);
        handler.apply(tx, storage);
        assertEquals(200, storage.get("banana"));
    }
}
