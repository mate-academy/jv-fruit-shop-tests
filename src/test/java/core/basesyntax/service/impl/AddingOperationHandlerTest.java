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

class AddingOperationHandlerTest {
    private static AddingOperationHandler addingOperationHandler;

    @BeforeAll
    static void beforeAll() {
        addingOperationHandler = new AddingOperationHandler();
    }

    @BeforeEach
    void beforeEach() {
        fruitStorage.clear();
    }

    @Test
    void handle_validInput_Ok() {
        final String banana = "banana";
        final String apple = "apple";
        final FruitTransaction bananas = new FruitTransaction(Operation.SUPPLY, banana, 1);
        final FruitTransaction apples = new FruitTransaction(Operation.RETURN, apple, 10);

        addingOperationHandler.handle(bananas);
        assertEquals(1, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(banana));
        assertEquals(bananas.getQuantity(), fruitStorage.get(bananas.getFruit()));

        addingOperationHandler.handle(bananas);
        assertEquals(1, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(banana));
        assertEquals(bananas.getQuantity() * 2, fruitStorage.get(bananas.getFruit()));

        addingOperationHandler.handle(apples);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(apple));
        assertEquals(apples.getQuantity(), fruitStorage.get(apple));
        assertTrue(fruitStorage.containsKey(banana));
        assertEquals(bananas.getQuantity() * 2, fruitStorage.get(banana));

        addingOperationHandler.handle(apples);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(apple));
        assertEquals(apples.getQuantity() * 2, fruitStorage.get(apple));
        assertTrue(fruitStorage.containsKey(banana));
        assertEquals(bananas.getQuantity() * 2, fruitStorage.get(banana));

        addingOperationHandler.handle(bananas);
        assertEquals(2, fruitStorage.size());
        assertTrue(fruitStorage.containsKey(apple));
        assertEquals(apples.getQuantity() * 2, fruitStorage.get(apple));
        assertTrue(fruitStorage.containsKey(banana));
        assertEquals(bananas.getQuantity() * 3, fruitStorage.get(banana));
    }

    @Test
    void handle_nullInput_notOk() {
        assertThrows(NullPointerException.class, () -> addingOperationHandler.handle(null),
                "NullPointerException expected");
    }
}
