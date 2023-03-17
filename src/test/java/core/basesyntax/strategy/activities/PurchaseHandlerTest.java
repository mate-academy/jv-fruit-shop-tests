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

class PurchaseHandlerTest {
    private static final String OPERATION_TYPE = "p";
    private static ActivitiesHandler handler;
    private static Map<String, Integer> expected;

    @BeforeAll
    static void beforeAll() {
        handler = new PurchaseHandler();
        expected = new HashMap<>();
    }

    @Test
    void operationWithValidDataOk() {
        expected.put("banana", 10);
        FruitStore.supplies.put("banana", 30);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    void purchaseOperationWithEmptySupplyNotOk() {
        assertThrows(RuntimeException.class,
                () -> handler.operation(new FruitTransaction(OPERATION_TYPE,
                "banana", 20)));
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
