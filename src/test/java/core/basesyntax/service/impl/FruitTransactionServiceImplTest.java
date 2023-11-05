package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransactionOperation;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.transaction.BalanceHandler;
import core.basesyntax.service.transaction.OperationHandler;
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
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        transactionService = new FruitTransactionServiceImpl(fruitStorageDao, operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void processTransactions_validTransactions_updatesStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransactionOperation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(19);

        List<FruitTransaction> fruitTransactions = List.of(fruitTransaction);
        transactionService.processTransactions(fruitTransactions);

        Map<String, Integer> expectedFruitMap = new HashMap<>();
        expectedFruitMap.put("banana", 19);

        assertEquals(expectedFruitMap, Storage.fruitTransactions);
    }

    private static Map<FruitTransactionOperation, OperationHandler>
                    createOperationMap() {
        Map<FruitTransactionOperation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransactionOperation.BALANCE, new BalanceHandler());
        return strategyMap;
    }
}
