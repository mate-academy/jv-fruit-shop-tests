package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;
    private static ShopService shopService;

    @AfterEach
    void tearDown() {
        Storage.getAllItems().clear();
    }

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OPERATION, new BalanceOperation());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void process_listNull_notOk() {
        String expected = "There are no transactions in the list";
        assertEquals(expected, assertThrows(RuntimeException.class,
                () -> shopService.process(null)).getMessage());
    }

    @Test
    void process_applyingTransactions_ok() {
        int expectedSize = 1;
        int expectedQuantity = 234;
        String fruit = "banana";
        List<FruitTransaction> transactionList = new ArrayList<>();
        transactionList.add(new FruitTransaction(OPERATION,fruit,expectedQuantity));
        shopService.process(transactionList);
        Map<String, Integer> fruits = Storage.getAllItems();
        assertEquals(expectedSize, fruits.size());
        assertEquals(expectedQuantity,fruits.get(fruit));
    }
}
