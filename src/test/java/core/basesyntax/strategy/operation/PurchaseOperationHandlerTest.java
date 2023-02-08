package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        operationHandler = new PurchaseOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantityTransaction_NotOk() {
        Storage.fruitStorage.put("apple", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        operationHandler.updateAmount(fruitTransaction);
    }

    @Test
    public void updateAmount_Ok() {
        Storage.fruitStorage.put("banana", 110);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.updateAmount(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.fruitStorage.get("banana");
        Assert.assertTrue(expected == actual);
    }
}
