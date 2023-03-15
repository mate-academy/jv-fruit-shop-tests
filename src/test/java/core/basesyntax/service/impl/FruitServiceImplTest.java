package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
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
import java.util.Optional;
import org.junit.After;
import org.junit.Test;

public class FruitServiceImplTest {

    @Test
    public void fruitService_return_Ok() {
        final List<FruitTransaction> actual = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 44),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 75));

        Map<FruitTransaction.Operation, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyActivitiesHandler());
        activitiesHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnActivitiesHandler());
        FruitStrategy fruitStrategyImpl = new FruitStrategyImpl(activitiesHandlerMap);
        FruitServiceImpl fruitServiceImpl = new FruitServiceImpl(fruitStrategyImpl);
        fruitServiceImpl.usageOfStrategy(actual);

        assertEquals(Optional.ofNullable(Storage.fruitStorage.get("orange")), Optional.of(119));
        assertEquals(Optional.ofNullable(Storage.fruitStorage.get("banana")), Optional.of(5));
        assertEquals(Optional.ofNullable(Storage.fruitStorage.get("apple")), Optional.of(75));
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_return_notOk() {
        List<FruitTransaction> actual = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 44));

        Map<FruitTransaction.Operation, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseActivitiesHandler());
        FruitStrategy fruitStrategyImpl = new FruitStrategyImpl(activitiesHandlerMap);
        FruitServiceImpl fruitServiceImpl = new FruitServiceImpl(fruitStrategyImpl);
        fruitServiceImpl.usageOfStrategy(actual);
    }
}