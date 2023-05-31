package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransaction.Operation purchaseOperation;
    private static FruitTransaction.Operation supplyOperation;
    private static FruitTransaction.Operation returnOperation;
    private static FruitTransaction.Operation balanceOperation;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new OperationStrategyImpl(map);
        purchaseOperation = FruitTransaction.Operation.PURCHASE;
        supplyOperation = FruitTransaction.Operation.SUPPLY;
        returnOperation = FruitTransaction.Operation.RETURN;
        balanceOperation = FruitTransaction.Operation.BALANCE;
    }

    @Test
    public void getHandlerByOperation_purchaseOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(purchaseOperation);
        Assert.assertEquals("Expected another handler: PurchaseOperationHandler",
                PurchaseOperationHandler.class,
                actual.getClass());
    }

    @Test
    public void getHandlerByOperation_supplyOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(supplyOperation);
        Assert.assertEquals("Expected another handler: SupplyOperationHandler",
                SupplyOperationHandler.class,
                actual.getClass());
    }

    @Test
    public void getHandlerByOperation_returnOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(returnOperation);
        Assert.assertEquals("Expected another handler: ReturnOperationHandler",
                ReturnOperationHandler.class,
                actual.getClass());
    }

    @Test
    public void getHandlerByOperation_balanceOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(balanceOperation);
        Assert.assertEquals("Expected another handler: BalanceOperationHandler",
                BalanceOperationHandler.class,
                actual.getClass());
    }
}
