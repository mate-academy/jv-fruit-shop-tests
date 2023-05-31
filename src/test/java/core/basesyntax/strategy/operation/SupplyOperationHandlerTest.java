package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        fruitTransaction = new FruitTransaction();
        operationHandler = new SupplyOperationHandlerImpl();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void updateAmount_ValidData_Ok() {
        Storage.fruitStorage.put("banana", 100);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.updateAmount(fruitTransaction);
        Integer expected = 200;
        Integer actual = Storage.fruitStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }
}
