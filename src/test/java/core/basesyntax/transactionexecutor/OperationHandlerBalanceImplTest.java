package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OperationHandlerBalanceImplTest {
    private OperationHandler operationHandlerBalance = new OperationHandlerBalanceImpl();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void checkWorkHandlerMethod_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        operationHandlerBalance.handle(fruitTransaction);
        Assert.assertEquals(expected, Storage.getStorage());
        Storage.getStorage().clear();
    }
}
