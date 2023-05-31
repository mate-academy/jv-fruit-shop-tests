package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static BalanceOperationHandlerImpl balanceOperationHandler;
    private static PurchaseOperationHandlerImpl purchaseOperationHandler;
    private static ReturnOperationHandlerImpl returnOperationHandler;
    private static SupplyOperationHandlerImpl supplyOperationHandler;
    private static final int BALANCE = 20;
    private static final int AMOUNT = 10;
    private static final int BALANCE_RESULT = 30;
    private static final int PURCHASE_RESULT = 10;
    private static final int RETURN_RESULT = 30;
    private static final int SUPPLY_RESULT = 30;

    @BeforeClass
    public static void setUp() {
        balanceOperationHandler = new BalanceOperationHandlerImpl();
        purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        returnOperationHandler = new ReturnOperationHandlerImpl();
        supplyOperationHandler = new SupplyOperationHandlerImpl();
    }

    @Test
    public void balanceOperationHandler_ok() {
        int actual = balanceOperationHandler.apply(BALANCE, AMOUNT);
        Assert.assertEquals(actual, BALANCE_RESULT);
    }

    @Test
    public void purchaseOperationHandler_ok() {
        int actual = purchaseOperationHandler.apply(BALANCE, AMOUNT);
        Assert.assertEquals(actual, PURCHASE_RESULT);
    }

    @Test
    public void returnOperationHandler_ok() {
        int actual = returnOperationHandler.apply(BALANCE, AMOUNT);
        Assert.assertEquals(actual, RETURN_RESULT);
    }

    @Test
    public void suppleOperationHandler_ok() {
        int actual = supplyOperationHandler.apply(BALANCE, AMOUNT);
        Assert.assertEquals(actual, SUPPLY_RESULT);
    }
}
