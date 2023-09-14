package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtractOperationHandlerTest {
    private static SubtractOperationHandler subtractOperationHandler;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeAll
    static void beforeAll() {
        subtractOperationHandler = new SubtractOperationHandler();
    }

    @BeforeEach
    void beforeEach() {
        fruitStorage.clear();
        fruitStorage.put(BANANA, 10);
        fruitStorage.put(APPLE, 10);
    }

    @Test
    void handle_validInput_Ok() {
        final FruitTransaction bananas = new FruitTransaction(Operation.PURCHASE, BANANA, 2);
        final FruitTransaction apples = new FruitTransaction(Operation.PURCHASE, APPLE, 3);

        subtractOperationHandler.handle(bananas);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(BANANA));
        assertEquals(10 - 2, fruitStorage.get(BANANA));

        subtractOperationHandler.handle(bananas);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(BANANA));
        assertEquals(8 - 2, fruitStorage.get(BANANA));

        subtractOperationHandler.handle(apples);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(APPLE));
        assertEquals(10 - 3, fruitStorage.get(APPLE));
        assertTrue(fruitStorage.containsKey(BANANA));
        assertEquals(6, fruitStorage.get(BANANA));

        subtractOperationHandler.handle(apples);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(APPLE));
        assertEquals(7 - 3, fruitStorage.get(APPLE));
        assertTrue(fruitStorage.containsKey(BANANA));
        assertEquals(6, fruitStorage.get(BANANA));

        subtractOperationHandler.handle(bananas);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(APPLE));
        assertEquals(4, fruitStorage.get(APPLE));
        assertTrue(fruitStorage.containsKey(BANANA));
        assertEquals(6 - 2, fruitStorage.get(BANANA));
    }

    @Test
    void handle_nullInput_notOk() {
        assertThrows(NullPointerException.class, () -> subtractOperationHandler.handle(null),
                "NullPointerException expected");
    }

    @Test
    void handle_negativeBalance_notOk() {
        FruitTransaction bananas = new FruitTransaction(Operation.PURCHASE, BANANA, 11);
        assertThrows(RuntimeException.class, () -> subtractOperationHandler.handle(bananas),
                "RuntimeException expected");
    }
}
