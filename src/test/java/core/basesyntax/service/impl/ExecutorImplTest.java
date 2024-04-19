package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Executor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operationhandlers.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandlers.OperationHandler;
import core.basesyntax.strategy.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.strategy.operationhandlers.ReturnOperationHandler;
import core.basesyntax.strategy.operationhandlers.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExecutorImplTest {
    private static StorageDao storageDao;
    private static final Map<FruitTransaction.Operation,
            OperationHandler> OPERATION_HANDLER_MAP = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private static Executor executor;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        executor = new ExecutorImpl(storageDao, operationStrategy);
    }

    @Test
    void execute_multipleTransactions_sameFruit() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.RETURN);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(10);
        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(5);
        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fourthTransaction.setFruit("banana");
        fourthTransaction.setQuantity(13);
        List<FruitTransaction> fruitTransactions = List.of(firstTransaction,
                secondTransaction,
                thirdTransaction,
                fourthTransaction);
        executor.execute(fruitTransactions);
        Map<String, Integer> expected = Map.of("banana", 22);
        assertEquals(expected, storageDao.getAll());
    }

    @Test
    void execute_multipleTransactions_differentFruits() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(15);
        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(5);
        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fourthTransaction.setFruit("apple");
        fourthTransaction.setQuantity(13);
        List<FruitTransaction> fruitTransactions = List.of(firstTransaction,
                secondTransaction,
                thirdTransaction,
                fourthTransaction);
        executor.execute(fruitTransactions);
        Map<String, Integer> expected = Map.of("banana", 25, "apple", 2);
        assertEquals(expected, storageDao.getAll());
    }

    @Test
    void execute_noTransactions_emptyStorage() {
        List<FruitTransaction> fruitTransactions = List.of();
        executor.execute(fruitTransactions);
        assertTrue(storageDao.getAll().isEmpty());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
