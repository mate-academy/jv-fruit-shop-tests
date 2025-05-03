package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void supplyHandler_validData_ok() {
        Storage.storage.put("banana", 15);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100
        );
        supplyOperationHandler.calculateOperation(fruitTransaction);
        int actualAmount = Storage.storage.get("banana");
        assertEquals(115, actualAmount);
    }

    @Test
    void supplyHandler_NegativeQuantity_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> supplyOperationHandler.calculateOperation(bananaTransaction));
    }

    @Test
    void supplyHandler_NullFruit_notOK() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 100
        );
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.calculateOperation(bananaTransaction));
    }

    @Test
    void supplyHandler_NullOperationType_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                null, "banana", 100
        );
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.calculateOperation(bananaTransaction));
    }
}
