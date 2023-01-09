package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperate_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        handler.operate(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.fruits.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operate_null_NotOk() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Transaction cannot be null");
        handler.operate(null);
    }
}
