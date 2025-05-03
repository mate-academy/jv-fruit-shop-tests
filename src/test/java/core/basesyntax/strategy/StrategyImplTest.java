package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.PurchaseOperationHandler;
import core.basesyntax.operations.ReturnOperationHandler;
import core.basesyntax.operations.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static Strategy strategy;
    private static Transaction transaction;

    @BeforeClass
    public static void init() {
        strategy = new StrategyImpl();
        transaction = new Transaction();
    }

    @Test
    public void get_balanceOperation_ok() {
        transaction.setOperation(Operation.BALANCE);
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_supplyOperation_ok() {
        transaction.setOperation(Operation.SUPPLY);
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_purchaseOperation_ok() {
        transaction.setOperation(Operation.PURCHASE);
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_returnOperation_ok() {
        transaction.setOperation(Operation.RETURN);
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }
}
