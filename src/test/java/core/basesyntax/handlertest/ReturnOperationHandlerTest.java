package core.basesyntax.handlertest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.DATABASE.clear();
    }

    @Test
    void handleTransaction_valid_ok() {
        Storage.DATABASE.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 30);
        assertDoesNotThrow(() -> returnOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("apple");
        assertEquals(130, actual);
    }

    @Test
    void handleTransaction_nonExistedFruitInShop_notOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "mango", 30);
        assertThrows(InvalidDataException.class,
                () -> returnOperationHandler.handleTransaction(transaction));
        assertEquals(0, Storage.DATABASE.size());
    }
}
