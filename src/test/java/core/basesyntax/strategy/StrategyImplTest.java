package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.PurchaseOperationHandler;
import core.basesyntax.operations.ReturnOperationHandler;
import core.basesyntax.operations.SupplyOperationHandler;
import org.junit.Test;

public class StrategyImplTest {
    private static Strategy strategy = new StrategyImpl();
    private static Transaction transaction = new Transaction();

    @Test
    public void get_balanceOperation_Ok() {
        transaction.setOperation(Operation.BALANCE);
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_supplyOperation_Ok() {
        transaction.setOperation(Operation.SUPPLY);
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_purchaseOperation_Ok() {
        transaction.setOperation(Operation.PURCHASE);
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void get_returnOperation_Ok() {
        transaction.setOperation(Operation.RETURN);
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = strategy.getOperationHandler(transaction).getClass();
        assertEquals(expected,actual);
    }
}
