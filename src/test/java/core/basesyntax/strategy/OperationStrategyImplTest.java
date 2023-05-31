package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new FruitBalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new FruitPurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new FruitReturnHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new FruitSupplyHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getHandler_isValid() {
        OperationHandler operationHandler = operationStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(FruitBalanceHandler.class, operationHandler.getClass());
    }

    @Test
    public void getHandler_isNotValid() {
        OperationHandler operationHandler = operationHandlerMap
                .get(FruitTransaction.Operation.HELL);
        assertNull(operationHandler);
    }
}
