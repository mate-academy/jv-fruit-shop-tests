package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    @BeforeAll
    static void clear() {
        Storage.storage.clear();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void when_ReturnExisting_Ok() {
        Storage.storage.put("banana", 0);
        OperationHandler returnOperation = new ReturnOperationHandler();
        FruitTransaction returnTransaction = new FruitTransaction(
                Operation.RETURN,
                "banana",
                5
        );
        returnOperation.processOperation(returnTransaction);
        Map<String, Integer> expected = Map.of("banana", 5);
        assertEquals(expected, Storage.storage);
    }

    @Test
    void when_ReturnNonExisting_NotOk() {
        Storage.storage.put("apple", 10);
        OperationHandler returnOperation = new ReturnOperationHandler();
        FruitTransaction returnTransaction = new FruitTransaction(
                Operation.RETURN,
                "banana",
                5
        );
        assertThrows(RuntimeException.class,
                () -> returnOperation.processOperation(returnTransaction));
    }
}
