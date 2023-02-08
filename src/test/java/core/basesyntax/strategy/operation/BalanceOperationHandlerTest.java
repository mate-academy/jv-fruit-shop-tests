package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        fruitTransaction = new FruitTransaction();
        operationHandler = new BalanceOperationHandlerImpl();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantityTransaction_NotOk() {
        fruitTransaction.setQuantity(-1);
        operationHandler.updateAmount(fruitTransaction);
    }

    @Test
    public void updateAmount_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.updateAmount(fruitTransaction);
        Integer expected = 100;
        Integer actual = Storage.fruitStorage.get("banana");
        Assert.assertTrue(expected == actual);
    }
}
