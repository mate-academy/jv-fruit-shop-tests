package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerImplTest {
    private static final String EXCEPTION_MESSAGE = "Exception should be thrown here!";
    private SupplyOperationHandlerImpl supplyOperationHandler;

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandlerImpl();
    }

    @Test
    void handle_validData_Ok() {
        Storage.dataStorage.put("banana", 34);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 16);
        Integer expected = 50;
        supplyOperationHandler.handle(fruit);
        Integer actual = Storage.dataStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_NotOk() {
        Storage.dataStorage.put("banana", 34);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -56);
        assertThrows(RuntimeException.class, () ->
                supplyOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
