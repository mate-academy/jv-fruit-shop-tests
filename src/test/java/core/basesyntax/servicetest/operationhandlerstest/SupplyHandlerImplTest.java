package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.SupplyHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerImplTest {
    @Test
    void supplyHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        assertEquals(150, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void supplyHandle_emptyStorage_Ok() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 287));
        assertEquals(287, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void handleNull_NotOk() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        assertThrows(
                NullPointerException.class, () -> supplyHandler.handleOperation(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
