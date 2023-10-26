package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
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
    private static FruitTransactionDao fruitTransactionDao;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();

        activityHandlerMap = new HashMap<>();

        activityHandlerMap.put(Operation.BALANCE,
                new BalanceActivityHandler(fruitTransactionDao));

        activityHandlerMap.put(Operation.SUPPLY,
                new SupplyActivityHandler(fruitTransactionDao));

        activityHandlerMap.put(Operation.PURCHASE,
                new PurchaseActivityHandler(fruitTransactionDao));

        activityHandlerMap.put(Operation.RETURN,
                new ReturnActivityHandler(fruitTransactionDao));

        typeActivityStrategy
                = new TypeActivityStrategyImpl(activityHandlerMap);
    }

    @Test
    void get_handlerFromSupplyOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.SUPPLY);
        ActivityHandler expected = new SupplyActivityHandler(fruitTransactionDao);
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void get_handlerFromReturnOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.RETURN);
        ActivityHandler expected = new ReturnActivityHandler(fruitTransactionDao);
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void get_handlerFromPurchaseOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.PURCHASE);
        ActivityHandler expected = new PurchaseActivityHandler(fruitTransactionDao);
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void get_handlerFromBalanceOperation_isOk() {
        ActivityHandler actual = typeActivityStrategy.get(Operation.BALANCE);
        ActivityHandler expected = new BalanceActivityHandler(fruitTransactionDao);
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void get_handlerFromNull_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> typeActivityStrategy.get(null));
    }
}
