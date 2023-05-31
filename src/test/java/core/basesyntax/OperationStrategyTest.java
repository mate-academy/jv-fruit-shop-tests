package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.operation.BalanceOperationHandler;
import core.basesyntax.strategy.impl.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.operation.ReturnOperationHandler;
import core.basesyntax.strategy.impl.operation.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_balanceOperation_ok() {
        Class<?> actual = BalanceOperationHandler.class;
        Class<?> expected = operationStrategy.getOperationHandler(
                FruitTransaction.Operation.BALANCE).getClass();
        assertEquals("Balance must be updated :", expected, actual);
    }

    @Test
    public void get_purchaseOperation_ok() {
        Class<?> actual = PurchaseOperationHandler.class;
        Class<?> expected = operationStrategy.getOperationHandler(
                FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals("Buyers purchased the product, balance: ",
                expected, actual);
    }

    @Test
    public void get_supplyOperation_ok() {
        Class<?> actual = SupplyOperationHandler.class;
        Class<?> expected = operationStrategy.getOperationHandler(
                FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals("You should accepting new fruits from suppliers, balance: ",
                expected, actual);
    }

    @Test
    public void get_returnOperation_ok() {
        Class<?> actual = ReturnOperationHandler.class;
        Class<?> expected = operationStrategy.getOperationHandler(
                FruitTransaction.Operation.RETURN).getClass();
        assertEquals("Buyer return some fruits, balance: ",
                expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void get_operationNullValue_notOk() {
        operationStrategy.getOperationHandler(null);
    }
}
