package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseHandler;
import core.basesyntax.strategy.operation.ReturnHandler;
import core.basesyntax.strategy.operation.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = new HashMap<>();
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlerMap);

    @Before
    public void setUp() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitDao));
    }

    @Test
    public void getHandler_Balance_Ok() {
        OperationHandler actualB = operationStrategy.getHandler(FruitTransaction
                .Operation.BALANCE);
        BalanceHandler expectedB = new BalanceHandler(fruitDao);
        assertEquals(expectedB.getClass(), actualB.getClass());
    }

    @Test
    public void getHandler_Purchase_Ok() {
        OperationHandler actualP = operationStrategy.getHandler(FruitTransaction
                .Operation.PURCHASE);
        PurchaseHandler expectedP = new PurchaseHandler(fruitDao);
        assertEquals(expectedP.getClass(), actualP.getClass());
    }

    @Test
    public void getHandler_Supply_Ok() {
        OperationHandler actualS = operationStrategy.getHandler(FruitTransaction
                .Operation.SUPPLY);
        SupplyHandler expectedS = new SupplyHandler(fruitDao);
        assertEquals(expectedS.getClass(), actualS.getClass());
    }

    @Test
    public void getHandler_Return_Ok() {
        OperationHandler actualR = operationStrategy.getHandler(FruitTransaction
                .Operation.RETURN);
        ReturnHandler expectedR = new ReturnHandler(fruitDao);
        assertEquals(expectedR.getClass(), actualR.getClass());
    }
}
