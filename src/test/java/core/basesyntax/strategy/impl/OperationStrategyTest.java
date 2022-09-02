package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static Map<Transaction.Operation, OperationHandler> map;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        map = new HashMap<>();
        map.put(Transaction.Operation.BALANCE, new BalanceOperationHandler());
        map.put(Transaction.Operation.SUPPLY, new SupplyOperationHandler());
        map.put(Transaction.Operation.RETURN, new ReturnOperationHandler());
        map.put(Transaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void operationHandlerSupply() {
        Class actualSupply = operationStrategy.getByOperation(Transaction.Operation.SUPPLY).getClass();
        Class expectedSupply = SupplyOperationHandler.class;
        assertEquals(actualSupply,expectedSupply);
    }

    @Test
    public void operationHandlerBalance() {
        Class expectedBalance = operationStrategy.getByOperation(Transaction.Operation.BALANCE).getClass();
        Class actualBalance = BalanceOperationHandler.class;
        assertEquals(actualBalance, expectedBalance);
    }

    @Test
    public void operationHandlerReturn() {
        Class expectedReturn = operationStrategy.getByOperation(Transaction.Operation.RETURN).getClass();
        Class actualReturn = ReturnOperationHandler.class;
        assertEquals(actualReturn, expectedReturn);
    }

    @Test
    public void operationHandlerPurchase() {
        Class expectedPurchase = operationStrategy.getByOperation(Transaction.Operation.PURCHASE).getClass();
        Class actualPurchase = PurchaseOperationHandler.class;
        assertEquals(actualPurchase,expectedPurchase);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
