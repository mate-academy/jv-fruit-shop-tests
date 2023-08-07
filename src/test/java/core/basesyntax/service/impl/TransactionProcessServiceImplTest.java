package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerService;
import core.basesyntax.service.OperationStrategyService;
import core.basesyntax.service.TransactionProcessService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessServiceImplTest {
    private static TransactionProcessService transactionProcessService;

    private static final String BANANA = "banana";
    private OperationStrategyService operationStrategyService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandlerService> strategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler());
        transactionProcessService = new TransactionProcessServiceImpl(operationStrategyService);
        operationStrategyService = new OperationStrategyImpl(strategyMap);
    }

    @Test
    void transactionProcessData_Ok() {
        transactionProcessService = new TransactionProcessServiceImpl(operationStrategyService);
        List<FruitTransaction> transactionList = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 100));
        transactionProcessService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(BANANA, 100);
        assertEquals(expectedStorage, Storage.fruitsStorage);
    }
}
