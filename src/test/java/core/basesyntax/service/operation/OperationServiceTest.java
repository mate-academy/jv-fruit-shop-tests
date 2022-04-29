package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.NotEnoughFruitsInStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.service.strategy.handlers.OperationHandler;
import core.basesyntax.service.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.service.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.service.strategy.handlers.SupplyOperationHandler;
import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceTest {
    private static OperationService operationService;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> map = new HashMap<>();
        StorageDao storageDao = new StorageDaoImpl();
        map.put("b", new BalanceOperationHandler(storageDao));
        map.put("r", new ReturnOperationHandler(storageDao));
        map.put("s", new SupplyOperationHandler(storageDao));
        map.put("p", new PurchaseOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(map);
        operationService = new OperationServiceImpl(operationStrategy);
    }

    @Test
    public void process_balanceAction_Ok() {
        int expected = 10;
        List<FruitTransaction> fruitTransactionList =
                List.of(new FruitTransaction("b","banana", expected));
        operationService.process(fruitTransactionList);
        int actual = Storage.dataBase.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_supplyAction_Ok() {
        Storage.dataBase.put("banana", 10);
        int expected = 15;
        List<FruitTransaction> fruitTransactionList =
                List.of(new FruitTransaction("s","banana", 5));
        operationService.process(fruitTransactionList);
        int actual = Storage.dataBase.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_purchaseAction_Ok() {
        Storage.dataBase.put("banana", 10);
        int expected = 5;
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("p",
                "banana", 5));
        operationService.process(fruitTransactionList);
        int actual = Storage.dataBase.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_returnAction_Ok() {
        Storage.dataBase.put("banana", 10);
        int expected = 15;
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("r",
                "banana", 5));
        operationService.process(fruitTransactionList);
        int actual = Storage.dataBase.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_emptyList_Ok() {
        operationService.process(Collections.EMPTY_LIST);
        assertTrue(Storage.dataBase.isEmpty());
    }

    @Test (expected = NotEnoughFruitsInStorage.class)
    public void process_purchaseInvalidData_NotOk() {
        operationService.process(List.of(new FruitTransaction("b",
                "banana", 10)));
        List<FruitTransaction> fruitTransactionList = List.of(new FruitTransaction("p",
                "banana", 11));
        operationService.process(fruitTransactionList);
    }

    @Test (expected = NullPointerException.class)
    public void executeTransaction_nullTransactions_NotOk() {
        operationService.process(null);
    }

    @After
    public void clearStorage() {
        Storage.dataBase.clear();
    }
}

