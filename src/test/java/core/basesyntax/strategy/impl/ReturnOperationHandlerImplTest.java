package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerImplTest {
    private static final String EXCEPTION_MESSAGE = "Exception should be thrown here!";
    private ReturnOperationHandlerImpl returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandlerImpl();
    }

    @Test
    void handle_validData_Ok() {
        Storage.dataStorage.put("banana", 34);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 45);
        Integer expected = 79;
        returnOperationHandler.handle(fruit);
        Integer actual = Storage.dataStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_NotOk() {
        Storage.dataStorage.put("banana", 34);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", -56);
        assertThrows(RuntimeException.class, () ->
                returnOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @Test
    void handle_invalidFruit_NotOk() {
        Storage.dataStorage.put("apple", 45);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 23);
        assertThrows(RuntimeException.class, () ->
                returnOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
