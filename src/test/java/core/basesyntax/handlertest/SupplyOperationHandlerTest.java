package core.basesyntax.handlertest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.DATABASE.clear();
    }

    @Test
    void handleTransaction_valid_ok() {
        Storage.DATABASE.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 30);
        assertDoesNotThrow(() -> supplyOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("apple");
        assertEquals(130, actual);
    }

    @Test
    void handleTransaction_nonExistedFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "mango", 30);
        assertDoesNotThrow(() -> supplyOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("mango");
        assertEquals(30, actual);
    }
}
