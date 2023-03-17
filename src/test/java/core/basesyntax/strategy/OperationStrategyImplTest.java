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
    private static final String DEFAULT_BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 20;
    private static FruitTransaction.Operation balanceOperation;
    private static FruitTransaction.Operation supplyOperation;
    private static FruitTransaction.Operation purchaseOperation;
    private static FruitTransaction.Operation returnOperation;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = FruitTransaction.Operation.BALANCE;
        supplyOperation = FruitTransaction.Operation.SUPPLY;
        purchaseOperation = FruitTransaction.Operation.PURCHASE;
        returnOperation = FruitTransaction.Operation.RETURN;
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operations);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(balanceOperation, DEFAULT_BANANA, DEFAULT_QUANTITY);
        String expected = BalanceOperationHandler.class.getSimpleName();
        String actual = operationStrategy.get(balanceOperation).getClass().getSimpleName();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperation_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(supplyOperation, DEFAULT_BANANA, DEFAULT_QUANTITY);
        String expected = SupplyOperationHandler.class.getSimpleName();
        String actual = operationStrategy.get(supplyOperation).getClass().getSimpleName();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperation_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(purchaseOperation, DEFAULT_BANANA, DEFAULT_QUANTITY);
        String expected = PurchaseOperationHandler.class.getSimpleName();
        String actual = operationStrategy.get(purchaseOperation).getClass().getSimpleName();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperation_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(returnOperation, DEFAULT_BANANA, DEFAULT_QUANTITY);
        String expected = ReturnOperationHandler.class.getSimpleName();
        String actual = operationStrategy.get(returnOperation).getClass().getSimpleName();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void get_operationIsNull_notOk() {
        operationStrategy.get(null);
        fail("You must throw Runtime Exception, if the operation is null");
    }
}
