package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        fruitTransaction = new FruitTransaction();
        operationHandler = new ReturnOperationHandlerImpl();
    }

    @Test
    public void updateAmount_Ok() {
        Storage.fruitStorage.put("banana", 100);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.updateAmount(fruitTransaction);
        Integer expected = 200;
        Integer actual = Storage.fruitStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }
}
