package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerBalanceImplTest {
    private OperationHandler operationHandlerBalance;
    private FruitTransaction fruitTransaction;

    @Before
    public void initializationOfVariables() {
        operationHandlerBalance = new OperationHandlerBalanceImpl();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void cleanStorage() {
        Storage.getStorage().clear();
    }

    @Test
    public void handlerBalance_checkWork_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        operationHandlerBalance.handle(fruitTransaction);
        Assert.assertEquals(expected, Storage.getStorage());
    }
}
