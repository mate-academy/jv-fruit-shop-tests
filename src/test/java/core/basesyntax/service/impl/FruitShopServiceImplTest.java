package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
    private StorageDao storageDao;
    private FruitShopService fruitShopService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        transactions = new ArrayList<>();

    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    void processData_validTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 50);
        transactions.add(transaction1);
        transactions.add(transaction2);
        fruitShopService.processData(transactions);
        assertEquals(20, storageDao.getFruitQuantity("banana"));
        assertEquals(50, storageDao.getFruitQuantity("apple"));
    }

    @Test
    void processData_nullTransactions_notOk() {
        transactions = null;
        assertThrowsException(transactions);
    }

    @Test
    void processData_emptyTransactions_ok() {
        fruitShopService.processData(transactions);
        assertEquals(0, storageDao.getAllFruits().size());
    }

    private void assertThrowsException(List<FruitTransaction> transactions) {
        assertThrows(IllegalArgumentException.class,
                () -> fruitShopService.processData(transactions));
    }
}
