package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private StorageDao storageDao;
    private OperationStrategy operationStrategy;
    private FruitShopService fruitShopService;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        operationStrategy = new FakeOperationStrategy();
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
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
    void processData_emptyTransactions_notOk() {
        fruitShopService.processData(transactions);
        assertEquals(0, storageDao.getAllFruits().size());
    }

    private void assertThrowsException(List<FruitTransaction> transactions) {
        assertThrows(IllegalArgumentException.class,
                () -> fruitShopService.processData(transactions));
    }

    private static class FakeOperationStrategy implements OperationStrategy {
        @Override
        public OperationHandler get(FruitTransaction.Operation operation) {
            return new FakeOperationHandler();
        }
    }

    private static class FakeOperationHandler implements OperationHandler {
        @Override
        public int operate(int quantity, int oldCount) {
            return quantity + oldCount;
        }
    }
}
