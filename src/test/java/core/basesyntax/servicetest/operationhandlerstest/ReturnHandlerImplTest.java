package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import core.basesyntax.service.serviceimpl.operationhandlers.ReturnHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerImplTest {
    private OperationHandler returnHandler;

    @Test
    void returnHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        returnHandler = new ReturnHandlerImpl();
        returnHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100));
        assertEquals(150, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void returnHandle_emptyStorage_Ok() {
        returnHandler = new ReturnHandlerImpl();
        returnHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100));
        assertEquals(100, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void handleNull_NotOk() {
        returnHandler = new ReturnHandlerImpl();
        assertThrows(
                NullPointerException.class, () -> returnHandler.handleOperation(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
