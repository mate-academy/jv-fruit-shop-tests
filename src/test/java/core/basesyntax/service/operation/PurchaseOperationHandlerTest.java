package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static Integer ZERO_QUANTITY = 0;
    private static Integer MORE_THAN_BALANCE = 101;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static FruitTransaction fruitTransactionB;
    private static FruitTransaction fruitTransactionP;
    private static FruitTransaction fruitTransactionInvalidDataP;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruitTransactionB = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 100);
        fruitTransactionP = new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", 13);
        fruitTransactionInvalidDataP = new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", -1);
    }

    @Test
    public void purchaseOperation_validData_ok() {
        balanceOperationHandler.handle(fruitTransactionB);
        Integer expectedQuantity = fruitTransactionB.getQuantity()
                - fruitTransactionP.getQuantity();
        purchaseOperationHandler.handle(fruitTransactionP);

        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test
    public void purchaseOperation_allFruits_ok() {
        balanceOperationHandler.handle(fruitTransactionB);
        fruitTransactionP.setQuantity(fruitTransactionB.getQuantity());
        Integer expectedQuantity = fruitTransactionB.getQuantity()
                - fruitTransactionP.getQuantity();
        purchaseOperationHandler.handle(fruitTransactionP);

        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_zeroQuantity_notOk() {
        fruitTransactionInvalidDataP.setQuantity(ZERO_QUANTITY);
        purchaseOperationHandler.handle(fruitTransactionInvalidDataP);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_negativeQuantity_notOk() {
        purchaseOperationHandler.handle(fruitTransactionInvalidDataP);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_moreThanBalance_notOk() {
        fruitTransactionInvalidDataP.setQuantity(MORE_THAN_BALANCE);
        purchaseOperationHandler.handle(fruitTransactionInvalidDataP);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

}
