package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProccessData;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProccessDataImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction fruitTransaction;
    private static ProccessData proccessData;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeAll
    static void beforeAll() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());

        fruitTransactionList = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
        operationStrategy = new OperationStrategyImpl();
        proccessData = new ProccessDataImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit("banana");
        fruitTransactionList.add(fruitTransaction);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void handleOperations_processCorrectData_Ok() {
        proccessData.handleOperations(List.of(fruitTransaction), handlerMap);
        int expected = 20;
        assertEquals(expected, Storage.STORAGE.get("banana"));
    }

    @Test
    void handleOperations_inputEmptyFruitTransactionList_NotOk() {
        List<FruitTransaction> fruitTransactionEmpty = new ArrayList<>();
        assertThrows(RuntimeException.class, () ->
                proccessData.handleOperations(fruitTransactionEmpty, handlerMap));
    }

    @Test
    void handleOperations_inputEmptyHandlersMap_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> emptyMap = new HashMap<>();
        assertThrows(RuntimeException.class, () ->
                proccessData.handleOperations(fruitTransactionList, emptyMap));
    }

    @Test
    void handleOperations_inputFruitTransactionWithNullOperation_NotOk() {
        FruitTransaction fruitTransactionNullOp = new FruitTransaction();
        fruitTransactionNullOp.setOperation(null);
        fruitTransactionNullOp.setFruit("banana");
        fruitTransactionNullOp.setQuantity(100);
        assertThrows(RuntimeException.class, () ->
                operationStrategy.getOperationHandler(fruitTransactionNullOp, handlerMap));
    }
}
