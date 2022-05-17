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
    private OperationHandler purchaseHandler = new PurchaseHandler();
    private OperationHandler returnHandler = new ReturnHandler();
    private OperationHandler supplyHandler = new SupplyHandler();

    @BeforeClass
    public static void beforeClass() {
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
    }

    @Test
    public void get_validDataCheckTheSameClasses_Ok() {
        OperationHandler handlerBalanceActual =
                operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler.getClass(), handlerBalanceActual.getClass());
        OperationHandler handlerSupplyActual =
                operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(supplyHandler.getClass(), handlerSupplyActual.getClass());
        OperationHandler handlerReturnActual =
                operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(returnHandler.getClass(), handlerReturnActual.getClass());
        OperationHandler handlerPurchaseActual =
                operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(purchaseHandler.getClass(), handlerPurchaseActual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void get_null_notOk() {
        operationStrategy.get(null);
    }
}
