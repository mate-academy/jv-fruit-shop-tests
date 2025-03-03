package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlersTest {
    private ReturnOperationHandlers returnOperationHandlers;

    @BeforeEach
    void setUp() {
        returnOperationHandlers = new ReturnOperationHandlers();
        FruitStorage.storage.clear();
    }

    @Test
    void handle_fruitExists_returnQuantity_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 10;
        Integer quantityToReturn = 5;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, quantityToReturn);
        returnOperationHandlers.handle(transaction);
        assertEquals(initialQuantity + quantityToReturn, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_fruitExists_returnNegativeQuantity_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 10;
        Integer quantityToReturn = -3;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, quantityToReturn);
        returnOperationHandlers.handle(transaction);
        assertEquals(initialQuantity + quantityToReturn, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_fruitDoesNotExist_notOk() {
        String fruit = "orange";
        Integer quantityToReturn = 5;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, quantityToReturn);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            returnOperationHandlers.handle(transaction);
        });
        assertEquals("Database does not contain: " + fruit, exception.getMessage());
    }
}
