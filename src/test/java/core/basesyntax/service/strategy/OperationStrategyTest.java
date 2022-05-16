package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);
    private OperationHandler balanceHandler = new BalanceHandler();

    @BeforeClass
    public static void beforeClass() {
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
    }

    @Test
    public void get_validDataTheSameClass_Ok() {
        OperationHandler handlerActual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler.getClass(), handlerActual.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void get_null_notOk() {
        OperationHandler handler = operationStrategy.get(null);
        handler.getClass();
    }
}
