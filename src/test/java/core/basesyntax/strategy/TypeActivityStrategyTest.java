package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.amount.ActivityHandler;
import core.basesyntax.service.amount.BalanceActivityHandler;
import core.basesyntax.service.amount.PurchaseActivityHandler;
import core.basesyntax.service.amount.ReturnActivityHandler;
import core.basesyntax.service.amount.SupplyActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TypeActivityStrategyTest {
    private static Map<Operation, ActivityHandler> activityHandlerMap;
    private static TypeActivityStrategy typeActivityStrategy;

    @BeforeAll
    static void beforeAll() {
        activityHandlerMap = new HashMap<>();

        activityHandlerMap.put(Operation.BALANCE,
                new BalanceActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.SUPPLY,
                new SupplyActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.PURCHASE,
                new PurchaseActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.RETURN,
                new ReturnActivityHandler(new FruitTransactionDaoImpl()));

        typeActivityStrategy
                = new TypeActivityStrategyImpl(activityHandlerMap);
    }

    @Test
    void getHandlerFromSupplyOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.SUPPLY);
        ActivityHandler expected = new SupplyActivityHandler(new FruitTransactionDaoImpl());
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void getHandlerFromReturnOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.RETURN);
        ActivityHandler expected = new ReturnActivityHandler(new FruitTransactionDaoImpl());
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void getHandlerFromPurchaseOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.PURCHASE);
        ActivityHandler expected = new PurchaseActivityHandler(new FruitTransactionDaoImpl());
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void getHandlerFromBalanceOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.BALANCE);
        ActivityHandler expected = new BalanceActivityHandler(new FruitTransactionDaoImpl());
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void getHandlerFromNull_isNotOk() {
        assertThrows(NullPointerException.class,
                () -> typeActivityStrategy.get(null));
    }
}
