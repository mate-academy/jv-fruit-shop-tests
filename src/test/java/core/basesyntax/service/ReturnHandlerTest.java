package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    void testHandleTransaction() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple",
                3);

        returnHandler.handleTransaction(transaction, storage);

        assertEquals(13, storage.get("apple"));

        assertEquals(5, storage.get("banana"));
    }

    @Test
    void testHandleTransactionWhenFruitNotInStorage() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple",
                3);

        returnHandler.handleTransaction(transaction, storage);

        assertEquals(3, storage.get("apple"));

        assertEquals(5, storage.get("banana"));
    }
}
