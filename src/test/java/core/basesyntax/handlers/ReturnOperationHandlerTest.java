package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
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
        Storage.storage.clear();
    }

    @Test
    void returnHandler_ValidData_ok() {
        Storage.storage.put("banana", 15);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100
        );
        returnOperationHandler.calculateOperation(fruitTransaction);
        int actualAmount = Storage.storage.get("banana");
        assertEquals(115, actualAmount);
    }

    @Test
    void returnHandler_negativeQuantity_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> returnOperationHandler.calculateOperation(bananaTransaction));
    }

    @Test
    void returnHandler_nullFruit_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, null, 100
        );
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.calculateOperation(bananaTransaction));
    }
}
