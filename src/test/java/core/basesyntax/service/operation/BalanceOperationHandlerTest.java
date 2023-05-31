package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static Integer ZERO_BALANCE = 0;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransactionB;
    private static FruitTransaction fruitTransactionInvalidDataB;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruitTransactionB = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 100);
        fruitTransactionInvalidDataB = new FruitTransaction(
                FruitTransaction.OperationType.BALANCE,
                "banana", -1);
    }

    @Test
    public void handle_validData_ok() {
        Integer expectedQuantity = fruitTransactionB.getQuantity();
        balanceOperationHandler.handle(fruitTransactionB);
        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test
    public void handle_zeroBalance_ok() {
        fruitTransactionB.setQuantity(ZERO_BALANCE);
        Integer expectedQuantity = fruitTransactionB.getQuantity();
        balanceOperationHandler.handle(fruitTransactionB);
        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeBalance_notOk() {
        balanceOperationHandler.handle(fruitTransactionInvalidDataB);
    }

    @Test(expected = RuntimeException.class)
    public void handle_null_notOk() {
        balanceOperationHandler.handle(null);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
