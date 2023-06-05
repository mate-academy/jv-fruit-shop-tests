package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.handler.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);

    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    void processData_validTransactions_ok() {
        transactions = new ArrayList<>();
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 50);
        transactions.add(transaction1);
        transactions.add(transaction2);
        fruitShopService.processData(transactions);
        assertEquals(20, Storage.fruitMap.getOrDefault("banana", 0));
        assertEquals(50, Storage.fruitMap.getOrDefault("apple", 0));
    }

    @Test
    void processData_nullTransactions_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fruitShopService.processData(transactions));
    }

    @Test
    void processData_emptyTransactions_ok() {
        transactions = new ArrayList<>();
        fruitShopService.processData(transactions);
        assertEquals(0, Storage.fruitMap.size());
    }
}
