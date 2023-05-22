package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import core.basesyntax.service.serviceimpl.operationhandlers.SupplyHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyHandlerImplTest {
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandlerImpl();
    }

    @Test
    void supplyHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        supplyHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        assertEquals(150, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void supplyHandle_emptyStorage_Ok() {
        supplyHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        assertEquals(100, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void handleNull_NotOk() {
        assertThrows(
                NullPointerException.class, () -> supplyHandler.handleOperation(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
