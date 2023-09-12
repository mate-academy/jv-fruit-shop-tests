package core.basesyntax.operationtype;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    private OperationHandler operationHandler = new PurchaseHandler();

    @Test
    public void purchaseHandler_CorrectInput_isOk() {
        fruitQuantity.put("banana", 200);
        FruitRecord fruitRecord = new FruitRecord("p", "banana", 120);
        operationHandler.apply(fruitRecord);
        Integer actual = fruitQuantity.get("banana");
        Integer expected = 80;
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_NotCorrectInput_ExceptionOk() {
        fruitQuantity.put("banana", 200);
        FruitRecord fruitRecord = new FruitRecord("p", "banana", 1020);
        operationHandler.apply(fruitRecord);
    }
}
