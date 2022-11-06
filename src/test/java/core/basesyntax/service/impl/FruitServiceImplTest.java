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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final FruitService fruitService = initFruitService();

    @Test
    public void doOperationWithNoTransactions_Ok() {
        List<FruitTransaction> emptyTransactions = new ArrayList<>();
        fruitService.doOperation(emptyTransactions);
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit, Integer> actual = FruitStorage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void doOperationWithNormalTransactions() {
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
        FruitStorage.storage.clear();
    }

    @Test
    public void doOperation_PurchaseMoreFruitsThanCurrentlyInStorage_notOk() {
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
