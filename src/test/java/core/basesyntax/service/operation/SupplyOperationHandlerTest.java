package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static Integer ZERO_QUANTITY = 0;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static FruitTransaction fruitTransactionB;
    private static FruitTransaction fruitTransactionInvalidDataS;
    private static FruitTransaction fruitTransactionS;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        fruitTransactionB = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 100);
        fruitTransactionInvalidDataS = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana", -1);
        fruitTransactionS = new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                "banana", 13);
    }

    @Test
    public void supplyOperation_validData_ok() {
        balanceOperationHandler.handle(fruitTransactionB);
        Integer expectedQuantity = fruitTransactionB.getQuantity()
                + fruitTransactionS.getQuantity();
        supplyOperationHandler.handle(fruitTransactionS);

        Assert.assertEquals(expectedQuantity,
                Storage.fruits.get(fruitTransactionB.getFruitName()));
    }

    @Test(expected = RuntimeException.class)
    public void supplyOperation_zeroQuantity_notOk() {
        fruitTransactionInvalidDataS.setQuantity(ZERO_QUANTITY);
        supplyOperationHandler.handle(fruitTransactionInvalidDataS);
    }

    @Test(expected = NullPointerException.class)
    public void supplyOperation_null_notOk() {
        supplyOperationHandler.handle(null);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
