package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.StorageTransaction;
import core.basesyntax.strategy.impl.BalanceTransactionHandler;
import core.basesyntax.strategy.impl.PurchaseTransactionHandler;
import core.basesyntax.strategy.impl.ReturnTransactionHandler;
import core.basesyntax.strategy.impl.SupplyTransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyTest {
    private static final Map<StorageTransaction.Operation,
            TransactionHandler> operations = new HashMap<>();
    private static final String DEFAULT_FRUIT = "defaultFruit";
    private static final int DEFAULT_QUANTITY = 1;
    private static Strategy strategy;
    private static StorageTransaction.Operation balanceOperation;
    private static StorageTransaction.Operation supplyOperation;
    private static StorageTransaction.Operation returnOperation;
    private static StorageTransaction.Operation purchaseOperation;

    @BeforeClass
    public static void setUp() {
        balanceOperation = StorageTransaction.Operation.BALANCE;
        supplyOperation = StorageTransaction.Operation.SUPPLY;
        returnOperation = StorageTransaction.Operation.RETURN;
        purchaseOperation = StorageTransaction.Operation.PURCHASE;

        operations.put(StorageTransaction.Operation.BALANCE, new BalanceTransactionHandler());
        operations.put(StorageTransaction.Operation.RETURN, new ReturnTransactionHandler());
        operations.put(StorageTransaction.Operation.SUPPLY, new SupplyTransactionHandler());
        operations.put(StorageTransaction.Operation.PURCHASE, new PurchaseTransactionHandler());

        strategy = new TransactionStrategy(operations);
    }

    @Test
    public void getHandler_getPurchaseHandler_ok() {
        StorageTransaction transaction = new StorageTransaction(
                purchaseOperation, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        String expected = PurchaseTransactionHandler.class.getSimpleName();
        String actual = strategy.getHandler(transaction).getClass().getSimpleName();
        assertEquals("Expected class name: PurchaseTransactionHandler" + " but was " + actual,
                expected, actual);
    }

    @Test
    public void getHandler_getBalanceHandler_ok() {
        StorageTransaction transaction = new StorageTransaction(
                balanceOperation, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        String expected = BalanceTransactionHandler.class.getSimpleName();
        String actual = strategy.getHandler(transaction).getClass().getSimpleName();
        assertEquals("Expected class name: BalanceTransactionHandler" + " but was " + actual,
                expected, actual);
    }

    @Test
    public void getHandler_getReturnHandler_ok() {
        StorageTransaction transaction = new StorageTransaction(
                returnOperation, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        String expected = ReturnTransactionHandler.class.getSimpleName();
        String actual = strategy.getHandler(transaction).getClass().getSimpleName();
        assertEquals("Expected class name: ReturnTransactionHandler" + " but was " + actual,
                expected, actual);
    }

    @Test
    public void getHandler_getSupplyHandler_ok() {
        StorageTransaction transaction = new StorageTransaction(
                supplyOperation, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        String expected = SupplyTransactionHandler.class.getSimpleName();
        String actual = strategy.getHandler(transaction).getClass().getSimpleName();
        assertEquals("Expected class name: SupplyTransactionHandler" + " but was " + actual,
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getHandler_getNotExistingHandler_notOk() {
        operations.remove(balanceOperation);
        StorageTransaction transaction = new StorageTransaction(
                balanceOperation, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        strategy.getHandler(transaction);
        fail("You should throw RuntimeException when there is no such handler");
    }

    @After
    public void after() {
        operations.put(StorageTransaction.Operation.BALANCE, new BalanceTransactionHandler());
    }
}
