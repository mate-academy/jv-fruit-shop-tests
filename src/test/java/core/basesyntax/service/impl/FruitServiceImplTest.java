package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationBalance;
import core.basesyntax.strategy.impl.OperationPurchase;
import core.basesyntax.strategy.impl.OperationReturn;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.OperationSupply;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final FruitService fruitService = initFruitService();

    @Test
    public void doOperation_noTransactions_ok() {
        fruitService.doOperation(Collections.emptyList());
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(Collections.emptyMap(), actual);
    }

    @Test
    public void doOperation_normalTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.getOperation("b"),
                        new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.getOperation("s"),
                        new Fruit("apple"), 30),
                new FruitTransaction(FruitTransaction.Operation.getOperation("p"),
                        new Fruit("apple"), 50),
                new FruitTransaction(FruitTransaction.Operation.getOperation("r"),
                        new Fruit("apple"), 1));
        fruitService.doOperation(transactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("apple"), 81);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void doOperation_purchaseMoreFruitsThanCurrentlyInStorage_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.getOperation("b"),
                        new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.getOperation("s"),
                        new Fruit("apple"), 30),
                new FruitTransaction(FruitTransaction.Operation.getOperation("p"),
                        new Fruit("apple"), 131));
        fruitService.doOperation(transactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("apple"), 0);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void doOperation_balance_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.getOperation("b"),
                        new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.getOperation("b"),
                        new Fruit("orange"), 20),
                new FruitTransaction(FruitTransaction.Operation.getOperation("b"),
                        new Fruit("banana"), 40));
        fruitService.doOperation(transactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("apple"), 100,
                new Fruit("orange"), 20,
                new Fruit("banana"), 40);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void doOperation_supply_ok() {
        FruitStorage.storage.put(new Fruit("apple"), 100);
        FruitStorage.storage.put(new Fruit("orange"), 100);
        FruitStorage.storage.put(new Fruit("banana"), 100);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.getOperation("s"),
                        new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.getOperation("s"),
                        new Fruit("orange"), 20),
                new FruitTransaction(FruitTransaction.Operation.getOperation("s"),
                        new Fruit("banana"), 30));
        fruitService.doOperation(transactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("apple"), 110,
                new Fruit("orange"), 120,
                new Fruit("banana"), 130);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void doOperation_return_ok() {
        FruitStorage.storage.put(new Fruit("apple"), 100);
        FruitStorage.storage.put(new Fruit("orange"), 100);
        FruitStorage.storage.put(new Fruit("banana"), 100);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.getOperation("r"),
                        new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.getOperation("r"),
                        new Fruit("orange"), 20),
                new FruitTransaction(FruitTransaction.Operation.getOperation("r"),
                        new Fruit("banana"), 30));
        fruitService.doOperation(transactions);
        Map<Fruit, Integer> expected = Map.of(new Fruit("apple"), 110,
                new Fruit("orange"), 120,
                new Fruit("banana"), 130);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.storage.clear();
    }

    private static FruitService initFruitService() {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = new HashMap<>();
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        operationServiceMap.put(FruitTransaction.Operation.BALANCE,
                new OperationBalance(fruitStorageDao));
        operationServiceMap.put(FruitTransaction.Operation.SUPPLY,
                new OperationSupply(fruitStorageDao));
        operationServiceMap.put(FruitTransaction.Operation.RETURN,
                new OperationReturn(fruitStorageDao));
        operationServiceMap.put(FruitTransaction.Operation.PURCHASE,
                new OperationPurchase(fruitStorageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationServiceMap);
        return new FruitServiceImpl(operationStrategy);
    }
}
