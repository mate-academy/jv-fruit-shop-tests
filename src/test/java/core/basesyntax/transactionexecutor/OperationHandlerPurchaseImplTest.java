package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerPurchaseImplTest {
    private OperationHandler operationHandlerPurchase;
    private FruitTransaction fruitTransaction;

    @Before
    public void initializationOfVariables() {
        operationHandlerPurchase = new OperationHandlerPurchaseImpl();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void cleanStorage() {
        Storage.getStorage().clear();
    }

    @Test
    public void handlerPurchase_checkWork_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        Storage.put("banana", 100);
        operationHandlerPurchase.handle(fruitTransaction);
        Integer expected = Storage.getStorage().get(fruitTransaction.getFruit());
        Assert.assertEquals(50, (int) expected);
    }
}
