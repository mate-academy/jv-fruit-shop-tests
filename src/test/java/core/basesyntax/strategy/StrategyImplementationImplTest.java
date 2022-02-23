package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalancesHandlerImpl;
import core.basesyntax.strategy.handler.Handler;
import core.basesyntax.strategy.handler.PurchaseHandlerImpl;
import core.basesyntax.strategy.handler.ReturnHandlerImpl;
import core.basesyntax.strategy.handler.SupplyHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplementationImplTest {
    private static StrategyImplementation strategyImplementation;
    private static HandlersStore handlersStore;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, Handler> temporaryMap = new HashMap<>();
        temporaryMap.put(FruitTransaction.Operation.BALANCE,new BalancesHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.PURCHASE,new PurchaseHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.SUPPLY,new SupplyHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.RETURN,new ReturnHandlerImpl());
        handlersStore = new HandlersStore(temporaryMap);
        strategyImplementation = new StrategyImplementationImpl(handlersStore);
        fruitTransactions = new ArrayList<>();
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
    }

    @Test(expected = RuntimeException.class)
    public void strategy_NullCheck_notOk() {
        strategyImplementation.strategy(null);
    }

    @Test(expected = RuntimeException.class)
    public void strategy_findInvalidOperation_notOk() {
        fruitTransactions.add(new FruitTransaction("a","test",43));
        strategyImplementation.strategy(fruitTransactions);
    }

    @Test
    public void strategy_getRightHandler_ok() {
        fruitTransactions.add(new FruitTransaction("b","test",13));
        fruitTransactions.add(new FruitTransaction("s","test",13));
        fruitTransactions.add(new FruitTransaction("r","test",13));
        fruitTransactions.add(new FruitTransaction("p","test",13));
        boolean[] expected = new boolean[4];
        expected[0] = HandlersStore.getStrategy()
                .get(fruitTransactions.get(0).getOperation())
                .getClass().equals(BalancesHandlerImpl.class);
        expected[1] = HandlersStore.getStrategy()
                .get(fruitTransactions.get(1).getOperation())
                .getClass().equals(SupplyHandlerImpl.class);
        expected[2] = HandlersStore.getStrategy()
                .get(fruitTransactions.get(2).getOperation()).getClass()
                .equals(ReturnHandlerImpl.class);
        expected[3] = HandlersStore.getStrategy()
                .get(fruitTransactions.get(3).getOperation())
                .getClass().equals(PurchaseHandlerImpl.class);
        boolean actual = true;
        for (boolean expectedAnswer: expected) {
            assertEquals(expectedAnswer,actual);
        }
    }
}
