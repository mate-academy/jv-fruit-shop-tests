package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static FruitTransaction bananaTransactionPurchase;
    private static FruitTransaction bananaTransactionBalance;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static PurchaseHandler purchaseStrategy;
    private static final int PURCHASE_RESULT = BALANCE - QUANTITY;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        purchaseStrategy = new PurchaseHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void purchaseStrategy_ok() {
        int actual = purchaseStrategy.handle(BALANCE, QUANTITY);
        int expected = PURCHASE_RESULT;
        Assert.assertEquals(expected, actual);
    }
}
