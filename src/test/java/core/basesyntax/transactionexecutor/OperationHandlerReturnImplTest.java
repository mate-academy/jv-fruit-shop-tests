package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class OperationHandlerReturnImplTest {
    private OperationHandler operationHandlerReturn = new OperationHandlerReturnImpl();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void handlerReturn_checkWork_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        Storage.put("banana", 100);
        operationHandlerReturn.handle(fruitTransaction);
        Integer expected = Storage.getStorage().get(fruitTransaction.getFruit());
        Assert.assertEquals(150, (int) expected);
        Storage.getStorage().clear();
    }
}
