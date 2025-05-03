package core.basesyntax.operationtype;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;
import org.junit.Assert;
import org.junit.Test;

public class ReturnHandlerTest {
    private OperationHandler operationHandler = new ReturnHandler();

    @Test
    public void returnHandler_CorrectInput_isOk() {
        fruitQuantity.put("banana", 200);
        FruitRecord fruitRecord = new FruitRecord("r", "banana", 120);
        operationHandler.apply(fruitRecord);
        Integer actual = fruitQuantity.get("banana");
        Integer expected = 320;
        Assert.assertEquals(expected, actual);
    }

}
