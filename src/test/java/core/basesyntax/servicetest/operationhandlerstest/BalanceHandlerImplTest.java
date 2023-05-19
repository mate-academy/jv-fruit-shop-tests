package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.BalanceHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerImplTest {
    private OperationHandler balanceHandler;

    @Test
    void balanceHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        balanceHandler = new BalanceHandlerImpl();
        balanceHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        assertEquals(150, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void balanceHandle_emptyStorage_Ok() {
        balanceHandler = new BalanceHandlerImpl();
        balanceHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        assertEquals(100, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void handleNull_NotOk() {
        balanceHandler = new BalanceHandlerImpl();
        assertThrows(
                NullPointerException.class, () -> balanceHandler.handleOperation(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
