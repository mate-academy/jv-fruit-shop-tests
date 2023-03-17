package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.BalanceOperationHandler;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperationHandler;
import core.basesyntax.strategy.operations.ReturnOperationHandler;
import core.basesyntax.strategy.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operations
            = new HashMap<>();
    private static final FruitTransaction.Operation BALANCE_OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY_OPERATION
            = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation PURCHASE_OPERATION
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN_OPERATION
            = FruitTransaction.Operation.RETURN;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operations);
    }

    @Test
    public void get_balanceOperation_ok() {
        OperationHandler expected = operationStrategy.get(BALANCE_OPERATION);
        OperationHandler actual = operationStrategy.get(BALANCE_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_supplyOperation_ok() {
        OperationHandler expected = operationStrategy.get(SUPPLY_OPERATION);
        OperationHandler actual = operationStrategy.get(SUPPLY_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_purchaseOperation_ok() {
        OperationHandler expected = operationStrategy.get(PURCHASE_OPERATION);
        OperationHandler actual = operationStrategy.get(PURCHASE_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_returnOperation_ok() {
        OperationHandler expected = operationStrategy.get(RETURN_OPERATION);
        OperationHandler actual = operationStrategy.get(RETURN_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test (expected = RuntimeException.class)
    public void get_operationIsNull_notOk() {
        operationStrategy.get(null);
        fail("You must throw Runtime Exception, if the operation is null");
    }
}
