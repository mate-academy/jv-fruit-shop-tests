package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.StrategyException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.activities.ActivitiesHandler;
import core.basesyntax.strategy.activities.BalanceActivitiesHandler;
import core.basesyntax.strategy.activities.PurchaseActivitiesHandler;
import core.basesyntax.strategy.activities.ReturnActivitiesHandler;
import core.basesyntax.strategy.activities.SupplyActivitiesHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private StorageDao storage;
    private FruitService fruitServiceImpl;

    @Before
    public void beforeEachTest() {
        Map<FruitTransaction.Operation, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnActivitiesHandler());
        FruitStrategy fruitStrategyImpl = new FruitStrategyImpl(activitiesHandlerMap);
        fruitServiceImpl = new FruitServiceImpl(fruitStrategyImpl);
        storage = new StorageDaoImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    public void fruitService_return_Ok() {
        final List<FruitTransaction> actual = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 44),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 75));
        fruitServiceImpl.usageOfStrategy(actual);

        assertEquals("Expected banana quantity 5, but actual: "
                + storage.get("banana"), storage.get("banana"), (Integer) 5);

        assertEquals("Expected apple quantity 75, but actual: "
                + storage.get("apple"), storage.get("apple"), (Integer) 75);

        assertEquals("Expected orange quantity 119, but actual: "
                + storage.get("orange"), storage.get("orange"), (Integer) 119);
    }

    @Test(expected = StrategyException.class)
    public void fruitService_return_notOk() {
        List<FruitTransaction> actual = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 44));

        fruitServiceImpl.usageOfStrategy(actual);
    }
}
