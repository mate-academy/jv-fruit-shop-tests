package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTests {
    private static FruitService fruitService;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitDao));
        activityHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitDao));
        activityHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitDao));
        activityHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(activityHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void processData_emptyFruitTransaction_ok() {
        fruitService.processData(Collections.emptyList());
        assertEquals(Collections.emptyMap(), Storage.fruits);
    }

    @Test
    public void processData_fruitTransactionSupply_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 100));

        fruitService.processData(fruitTransactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 120, new Fruit("apple"), 200);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_fruitTransactionPurchase_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 20));

        fruitService.processData(fruitTransactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 10, new Fruit("apple"), 80);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_fruitTransactionReturn_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("banana"), 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 50));

        fruitService.processData(fruitTransactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 40, new Fruit("apple"), 150);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_allFruitTransactions_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 5));

        fruitService.processData(fruitTransactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 115, new Fruit("apple"), 110);
        assertEquals(expected, Storage.fruits);
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
        Storage.fruits.clear();
    }

}
