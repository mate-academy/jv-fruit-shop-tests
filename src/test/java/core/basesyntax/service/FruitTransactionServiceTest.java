package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<FruitTransaction> fruitTransactionList;
    private static List<FruitTransaction> fruitTransactionListEmpty;
    private static List<FruitTransaction> fruitTransactionListNullOperation;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeAll
    static void beforeAll() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        fruitTransactionService = new FruitTransactionServiceImpl(
                new OperationStrategyImpl(handlerMap));
    }

    @BeforeEach
    void setUp() {
        fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20));

        fruitTransactionListNullOperation = List.of(
                new FruitTransaction(null, "banana", 20));
        fruitTransactionListEmpty = new ArrayList<>();
    }

    @Test
    void transaction_rigthData_ok() {
        fruitTransactionService.processData(fruitTransactionList);
        assertEquals(Storage.totalFruit.get("banana"), 120);
        assertEquals(Storage.totalFruit.get("apple"), 30);
    }

    @Test
    void transaction_listIsEmpty_ok() {
        fruitTransactionService.processData(fruitTransactionListEmpty);
        assertTrue(Storage.totalFruit.isEmpty());
    }

    @Test
    void transaction_missingOperation_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processData(fruitTransactionListNullOperation));
    }
}
