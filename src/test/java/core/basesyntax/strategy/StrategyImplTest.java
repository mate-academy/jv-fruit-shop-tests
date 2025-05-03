package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.operations.BalanceOperationHandler;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperationHandler;
import core.basesyntax.strategy.operations.ReturnOperationHandler;
import core.basesyntax.strategy.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static Strategy operationStrategy;
    private static Transaction.Operation purchaseOperation;
    private static Transaction.Operation supplyOperation;
    private static Transaction.Operation returnOperation;
    private static Transaction.Operation balanceOperation;

    @BeforeClass
    public static void beforeClass() {
        Map<Transaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(Transaction.Operation.BALANCE, new BalanceOperationHandler());
        map.put(Transaction.Operation.SUPPLY, new SupplyOperationHandler());
        map.put(Transaction.Operation.RETURN, new ReturnOperationHandler());
        map.put(Transaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new StrategyImpl(map);
        purchaseOperation = Transaction.Operation.PURCHASE;
        supplyOperation = Transaction.Operation.SUPPLY;
        returnOperation = Transaction.Operation.RETURN;
        balanceOperation = Transaction.Operation.BALANCE;
    }

    @Test
    public void balanceOperation_ok() {
        OperationHandler actual = operationStrategy.getByOperation(balanceOperation);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void purchaseOperation_ok() {
        OperationHandler actual = operationStrategy.getByOperation(purchaseOperation);
        Assert.assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void supplyOperation_ok() {
        OperationHandler actual = operationStrategy.getByOperation(supplyOperation);
        Assert.assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void returnOperation_ok() {
        OperationHandler actual = operationStrategy.getByOperation(returnOperation);
        Assert.assertEquals(ReturnOperationHandler.class, actual.getClass());
    }
}
