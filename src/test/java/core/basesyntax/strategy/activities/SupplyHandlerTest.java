package core.basesyntax.strategy.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStore;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String OPERATION_TYPE = "s";
    private static ActivitiesHandler handler;
    private static Map<String, Integer> expected;

    @BeforeAll
    static void beforeAll() {
        handler = new SupplyHandler();
        expected = new HashMap<>();
    }

    @Test
    void operationWithValidDataOk() {
        expected.put("banana", 20);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    void threeOperationsWithValidDataOk() {
        expected.put("banana", 30);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 7));
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 3));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    void operationFromNullNotOk() {
        assertThrows(RuntimeException.class, () -> handler.operation(null));
    }

    @AfterEach
    void tearDown() {
        FruitStore.supplies.clear();
        expected.clear();
    }
}
