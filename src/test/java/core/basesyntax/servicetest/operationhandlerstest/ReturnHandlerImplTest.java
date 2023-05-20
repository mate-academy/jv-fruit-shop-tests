package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import core.basesyntax.service.serviceimpl.operationhandlers.ReturnHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnHandlerImplTest {
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandlerImpl();
    }

    @Test
    void returnHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        returnHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100));
        assertEquals(150, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void returnHandle_emptyStorage_Ok() {
        returnHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100));
        assertEquals(100, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void handleNull_NotOk() {
        assertThrows(
                NullPointerException.class, () -> returnHandler.handleOperation(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
