package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.convertor.DataConvertorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,new SupplyOperation());
        Storage.storage.put("banana", 19);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void handlerOk() {
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.BALANCE));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.PURCHASE));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.RETURN));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.SUPPLY));
        assertFalse(operationHandlers.isEmpty());
        Storage.storage.put("banana", 10);
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        new PurchaseOperation().handle(Storage.storage,purchaseTransaction);
        assertEquals(5,Storage.storage.get("banana"));

        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 5);

        new BalanceOperation().handle(Storage.storage, balanceTransaction);
        assertEquals(5, Storage.storage.get("banana"));

        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5);

        new ReturnOperation().handle(Storage.storage,returnTransaction);
        assertEquals(10,Storage.storage.get("banana"));

        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        new SupplyOperation().handle(Storage.storage,supplyTransaction);
        assertEquals(15, Storage.storage.get("banana"));
    }

    @Test
    void handlerNotOk() {
        assertFalse(operationHandlers.containsValue(new DataConvertorImpl()));
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 5);
        assertThrows(IllegalArgumentException.class, () ->
                new PurchaseOperation().handle(Storage.storage,purchaseTransaction));
    }
}
