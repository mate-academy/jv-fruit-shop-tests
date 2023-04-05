package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static Integer ZERO_QUANTITY = 0;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static FruitTransaction fruitTransactionB;
    private static FruitTransaction fruitTransactionInvalidDataR;
    private static FruitTransaction fruitTransactionR;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        fruitTransactionB = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 100);
        fruitTransactionInvalidDataR = new FruitTransaction(
                FruitTransaction.OperationType.RETURN,
                "banana", -1);
        fruitTransactionR = new FruitTransaction(FruitTransaction.OperationType.RETURN,
                "banana", 13);
    }

    @Test
    public void returnOperation_validData_ok() {
        balanceOperationHandler.handle(fruitTransactionB);
        Integer expectedQuantity = fruitTransactionB.getQuantity()
                + fruitTransactionR.getQuantity();
        OperationHandler operationHandlerR = new ReturnOperationHandler();
        operationHandlerR.handle(fruitTransactionR);

        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test(expected = NullPointerException.class)
    public void returnOperation_null_notOk() {
        returnOperationHandler.handle(null);
    }

    @Test(expected = NullPointerException.class)
    public void returnOperation_zeroQuantity_notOk() {
        fruitTransactionInvalidDataR.setQuantity(ZERO_QUANTITY);
        returnOperationHandler.handle(fruitTransactionInvalidDataR);
    }

    @Test(expected = NullPointerException.class)
    public void returnOperation_negativeQuantity_notOk() {
        returnOperationHandler.handle(fruitTransactionInvalidDataR);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
