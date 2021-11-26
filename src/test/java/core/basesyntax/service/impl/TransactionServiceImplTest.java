package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.bd.dao.StorageDao;
import core.basesyntax.bd.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.operationhandler.Operation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.impl.AddOperation;
import core.basesyntax.service.operationhandler.impl.SubtractOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static StorageDao storageDao;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put(Operation.BALANCE, new AddOperation(storageDao));
        operationHandlerMap.put(Operation.SUPPLY, new AddOperation(storageDao));
        operationHandlerMap.put(Operation.PURCHASE, new SubtractOperation(storageDao));
        operationHandlerMap.put(Operation.RETURN, new AddOperation(storageDao));
        transactionService = new TransactionServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitStorage.clear();
        storageDao.add(new Fruit("apple"), 5);
        storageDao.add(new Fruit("banana"), 10);
    }

    @Test
    public void applyTransactions_validData_Ok() {
        Fruit apple = new Fruit("apple");
        List<Transaction> transactionList = List.of(new Transaction(Operation.SUPPLY, apple, 10));
        transactionService.applyTransactions(transactionList);
        int expected = 15;
        int actual = storageDao.getFruitQuantity(apple);
        assertEquals(expected, actual);
    }
}
