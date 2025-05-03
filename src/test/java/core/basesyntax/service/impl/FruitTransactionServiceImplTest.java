package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.transactions.BalanceOperationHandler;
import core.basesyntax.service.transactions.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitTransactionService transactionService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(createOperationMap());
        transactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void addOrUpdate_validTransaction_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",19);
        Map<String, Integer> fruitMap =
                transactionService.addOrUpdate(List.of(fruitTransaction));
        int expectedSize = 1;
        assertEquals(expectedSize,fruitMap.size(),"size should be 1, added not properly ");
        assertEquals(expectedSize,Storage.fruitTransactions.size(),
                "size should be 1, added not properly ");
        int expectedQuantity = 19;
        assertEquals(expectedQuantity,fruitMap.get("banana"));
    }

    private static Map<FruitTransaction.Operation, OperationHandler>
            createOperationMap() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE,new BalanceOperationHandler());
        return strategyMap;
    }
}
