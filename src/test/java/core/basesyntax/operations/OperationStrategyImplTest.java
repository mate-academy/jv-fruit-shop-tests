package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.operations.impl.BalanceHandler;
import core.basesyntax.operations.impl.PurchaseHandler;
import core.basesyntax.operations.impl.ReturnHandler;
import core.basesyntax.operations.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitsDao;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitDaoImpl();
        operationHandlerMap.put("b", new BalanceHandler(fruitsDao));
        operationHandlerMap.put("p", new PurchaseHandler(fruitsDao));
        operationHandlerMap.put("s", new SupplyHandler(fruitsDao));
        operationHandlerMap.put("r", new ReturnHandler(fruitsDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getStrategy_inputWrongStrategy_notOk() {
        OperationHandler operationHandler = operationStrategy.getOperationHandler("w");
        assertNull(operationHandler);
    }

    @Test
    public void getStrategy_inputValidStrategyPurchase_ok() {
        OperationHandler operationHandler = operationStrategy.getOperationHandler("p");
        assertEquals(PurchaseHandler.class, operationHandler.getClass());
    }
}

