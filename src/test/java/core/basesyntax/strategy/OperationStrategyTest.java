package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(operations);
    }

    @Before
    public void setUp() {
        Storage.storage.put("apple", 200);
    }

    @Test
    public void strategy_balanceOperationStrategy_ok() {
        Integer expected = 200;
        Integer actual = Storage.storage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_supplyOperationStrategy_ok() {
        FruitTransaction supply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        operationStrategy.process(supply);
        Integer expected = 220;
        Integer actual = Storage.storage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_returnOperationStrategy_ok() {
        FruitTransaction returnOperation = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
        operationStrategy.process(returnOperation);
        Integer expected = 220;
        Integer actual = Storage.storage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_purchaseOperationStrategy_ok() {
        FruitTransaction returnOperation = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        operationStrategy.process(returnOperation);
        Integer expected = 180;
        Integer actual = Storage.storage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void strategy_returnToEmptyStorage_notOk() {
        tearDown();
        FruitTransaction returnOperation = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
        operationStrategy.process(returnOperation);
    }

    @Test(expected = NullPointerException.class)
    public void strategy_purchaseFromEmptyStorage_notOk() {
        tearDown();
        FruitTransaction returnOperation = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        operationStrategy.process(returnOperation);
    }

    @Test(expected = NullPointerException.class)
    public void strategy_supplyToEmptyStorage_notOk() {
        tearDown();
        FruitTransaction supply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        operationStrategy.process(supply);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
