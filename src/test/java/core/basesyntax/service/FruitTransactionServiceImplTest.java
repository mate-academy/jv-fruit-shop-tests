package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static List<FruitTransaction> fruitTransactionList;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitTransactionService fruitTransactionService;
    private static Fruit apple;

    @BeforeClass
    public static void setUp() {
        apple = new Fruit("apple");
        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction("b", apple, 20));
        fruitTransactionList.add(new FruitTransaction("p", apple, 10));
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitOperation.BALANCE.getOperation(),
                new BalanceOperationHandlerImpl());
        operationHandlerMap.put(FruitOperation.PURCHASE.getOperation(),
                new PurchaseOperationHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @Test
    public void addToStorage_ok() {
        int expected = 10;
        fruitTransactionService.addToStorage(fruitTransactionList);
        int actual = FruitStorage.fruitStorage.get(apple);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void addToStorage_emptyList_ok() {
        fruitTransactionService.addToStorage(Collections.emptyList());
        Assert.assertTrue(FruitStorage.fruitStorage.isEmpty());
    }

    @After
    public void cleanStorage() {
        FruitStorage.fruitStorage.remove(apple);
    }
}
