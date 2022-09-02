package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.StorageFruits;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StoreService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operations.BalanceOperation;
import core.basesyntax.strategy.operations.DailyOperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperation;
import core.basesyntax.strategy.operations.ReturnOperation;
import core.basesyntax.strategy.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StoreServiceImplTest {
    private StoreService storeService;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, DailyOperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        storeService = new StoreServiceImpl(fruitDao, operationStrategy);
    }

    @Test (expected = RuntimeException.class)
    public void processTransaction_dailyTransactionsNull_notOk() {
        storeService.processTransaction(null);
    }

    @Test (expected = RuntimeException.class)
    public void processTransaction_dailyTransactionsEmpty_notOk() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        storeService.processTransaction(emptyList);
    }

    @Test
    public void processTransaction_Ok() {
        List<Fruit> expectedList = new ArrayList<>();
        expectedList.add(new Fruit("orange", 1520));
        expectedList.add(new Fruit("kiwi", 900));
        List<FruitTransaction> dailyTransactionsList = new ArrayList<>();
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "orange", 200));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "kiwi", 1000));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "orange", 1000));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 130));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN, "kiwi", 100));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "kiwi", 200));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 50));
        dailyTransactionsList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "orange", 500));
        int actual = 0;
        List<Fruit> actualList = storeService.processTransaction(dailyTransactionsList);
        for (int i = 0; i < actualList.size(); i++) {
            if (actualList.get(i).equals(expectedList.get(i))) {
                actual++;
            }
        }
        int expected = 2;
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        StorageFruits.fruits.clear();
    }
}
