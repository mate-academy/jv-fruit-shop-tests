package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.STORAGE.clear();
    }

    @Test
    void processTransaction_valid_ok() {
        FruitStorage.STORAGE.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 30);
        assertDoesNotThrow(() -> returnOperationHandler.process(transaction));

        int availableAmount = FruitStorage.STORAGE.get("apple");
        assertEquals(130, availableAmount);
    }
}
