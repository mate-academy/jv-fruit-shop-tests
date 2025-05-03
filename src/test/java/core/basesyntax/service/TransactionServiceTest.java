package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
    private static final String BANANA = "banana";
    private static TransactionService transactionService;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> strategyMap;
    private static FruitDao fruitDao;
    private static List<FruitTransaction> testData;

    @BeforeAll
    static void beforeAll() {
        strategyMap = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationStrategy = new OperationStrategyImpl(strategyMap);
        fruitDao = new FruitDaoImpl();
        transactionService = new TransactionServiceImpl(operationStrategy, fruitDao);
        testData = new ArrayList<>();
        testData.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20));
    }

    @Test
    void processTransactions_validData_ok() {
        Map<String, Integer> expected = Map.of(BANANA,20);
        transactionService.processTransactions(testData);
        assertEquals(expected, Storage.getStock());
    }

    @Test
    void processTransactions_invalidData_notOk() {
        assertThrows(NullPointerException.class,
                () -> transactionService.processTransactions(null));
    }

    @AfterEach
    void tearDown() {
        Storage.getStock().clear();
    }
}
