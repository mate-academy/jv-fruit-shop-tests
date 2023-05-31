package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.impl.BalanceService;
import core.basesyntax.strategy.impl.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseService;
import core.basesyntax.strategy.impl.ReturnService;
import core.basesyntax.strategy.impl.SupplyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static Map<Operation, OperationHandler> operationStrategyMap;
    private static List<FruitTransaction> fruitTransactions;
    private FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        operationStrategyMap = new HashMap<>();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
        fruitDao = new FruitDaoImpl();
        operationStrategyMap.put(Operation.BALANCE, new BalanceService());
        operationStrategyMap.put(Operation.SUPPLY, new SupplyService());
        operationStrategyMap.put(Operation.PURCHASE, new PurchaseService());
        operationStrategyMap.put(Operation.RETURN, new ReturnService());
    }

    @Test
    public void balanceService_ok() {
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        int expected = 20;
        fruitService.doOperationService(fruitTransactions);
        int actual = fruitDao.get("banana").get().getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseService_ok() {
        Storage.fruitStore.add(new Fruit("apple", 30));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE, "apple", 20));
        int expected = 10;
        fruitService.doOperationService(fruitTransactions);
        int actual = fruitDao.get("apple").get().getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void returnService_ok() {
        Storage.fruitStore.add(new Fruit("apple", 40));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN, "apple", 15));
        int expected = 55;
        fruitService.doOperationService(fruitTransactions);
        int actual = fruitDao.get("apple").get().getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void supplyService_ok() {
        Storage.fruitStore.add(new Fruit("banana", 20));
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 30));
        int expected = 50;
        fruitService.doOperationService(fruitTransactions);
        int actual = fruitDao.get("banana").get().getQuantity();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void doOperationService_WithEmptyStorage_notOk() {
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 30));
        fruitService.doOperationService(fruitTransactions);
        int actual = fruitDao.get("banana").get().getQuantity();
    }

    @After
    public void tearDown() {
        Storage.fruitStore.clear();
    }
}
