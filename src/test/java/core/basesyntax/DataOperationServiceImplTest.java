package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataOperationService;
import core.basesyntax.service.impl.DataOperationServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataOperationServiceImplTest {
    private static DataOperationService dataOperationService;
    private static StorageDao storageDao;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        dataOperationService = new DataOperationServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void processOperation_goodOperation_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 50));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 30));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 80);
        expected.put("apple", 30);
        dataOperationService.processOperation(fruitTransactions);
        Map<String, Integer> actual = storageDao.getData();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeBalanceOperation_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", -20));
        dataOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativePurchaseOperation_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", -50));
        dataOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_invalidPurchaseOperation_notOk() {
        storageDao.putNewValues("banana", 10);
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 20));
        dataOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeSupplyOperation_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", -10));
        dataOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeReturnOperation_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", -40));
        dataOperationService.processOperation(fruitTransactions);
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
    }
}
