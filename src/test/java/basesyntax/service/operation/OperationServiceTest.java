package basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.model.FruitTransaction;
import basesyntax.service.strategy.OperationStrategy;
import basesyntax.service.strategy.OperationStrategyImpl;
import basesyntax.service.strategy.handlers.BalanceOperationHandler;
import basesyntax.service.strategy.handlers.OperationHandler;
import basesyntax.service.strategy.handlers.PurchaseOperationHandler;
import basesyntax.service.strategy.handlers.ReturnOperationHandler;
import basesyntax.service.strategy.handlers.SupplyOperationHandler;
import basesyntax.storage.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceTest {
    private static OperationService operationService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler(storageDao));
        map.put("r", new ReturnOperationHandler(storageDao));
        map.put("s", new SupplyOperationHandler(storageDao));
        map.put("p", new PurchaseOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(map);
        operationService = new OperationServiceImpl(operationStrategy);

    }

    @Test
    public void process_balanceAction_OK() {
        int expected = 10;
        List<FruitTransaction> fruitTransactionList =
                List.of(new FruitTransaction("b","banana", expected));
        operationService.process(fruitTransactionList);
        int actual = storageDao.getByKey("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_supplyAction_OK() {
        operationService.process(List.of(new FruitTransaction("b","banana", 10)));
        int expected = 15;
        List<FruitTransaction> fruitTransactionList =
                List.of(new FruitTransaction("s","banana", 5));
        operationService.process(fruitTransactionList);
        int actual = storageDao.getByKey("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_purchaseAction_OK() {
        operationService.process(List.of(new FruitTransaction("b",
                "banana", 10)));
        int expected = 5;
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("p",
                "banana", 5));
        operationService.process(fruitTransactionList);
        int actual = storageDao.getByKey("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_returnAction_OK() {
        operationService.process(List.of(new FruitTransaction("b",
                "banana", 10)));
        int expected = 15;
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("r",
                "banana", 5));
        operationService.process(fruitTransactionList);
        int actual = storageDao.getByKey("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_emptyList_OK() {
        Storage.dataBase.clear();
        operationService.process(Collections.EMPTY_LIST);
        assertTrue(storageDao.getDataBase().isEmpty());
    }

    @Test (expected = RuntimeException.class)
    public void process_purchaseInvalidData_NotOk() {
        operationService.process(List.of(new FruitTransaction("b",
                "banana", 10)));
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("p",
                "banana", 11));
        operationService.process(fruitTransactionList);
    }

    @Test (expected = NullPointerException.class)
    public void executeTransaction_NullTransactions_NotOk() {
        operationService.process(null);
    }
}

