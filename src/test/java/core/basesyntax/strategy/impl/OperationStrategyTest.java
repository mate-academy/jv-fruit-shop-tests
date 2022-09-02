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
    public static void beforeClass() throws Exception {
        map = new HashMap<>();
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void operationHandlerSupply() {
        OperationHandler supply = new SupplyOperationHandler();
        map.put(Transaction.Operation.SUPPLY, supply);
        assertEquals(supply, operationStrategy.getByOperation(Transaction.Operation.SUPPLY));
    }

    @Test
    public void operationHandlerBalance() {
        OperationHandler balance = new BalanceOperationHandler();
        map.put(Transaction.Operation.BALANCE, balance);
        assertEquals(balance, operationStrategy.getByOperation(Transaction.Operation.BALANCE));
    }

    @Test
    public void operationHandlerReturn() {
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        map.put(Transaction.Operation.RETURN, returnOperationHandler);
        assertEquals(returnOperationHandler, operationStrategy
                .getByOperation(Transaction.Operation.RETURN));
    }

    @Test
    public void operationHandlerPurchase() {
        OperationHandler purchase = new BalanceOperationHandler();
        map.put(Transaction.Operation.PURCHASE, purchase);
        assertEquals(purchase, operationStrategy.getByOperation(Transaction.Operation.PURCHASE));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getStorage().clear();
    }
}
