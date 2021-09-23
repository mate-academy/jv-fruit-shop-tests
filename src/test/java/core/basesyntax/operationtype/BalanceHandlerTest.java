package core.basesyntax.operationtype;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;
import org.junit.Assert;
import org.junit.Test;

public class BalanceHandlerTest {
    private OperationHandler operationHandler = new BalanceHandler();

    @Test
    public void balanceHandler_CorrectInput_isOk() {
        fruitQuantity.put("banana", 200);
        FruitRecord fruitRecord = new FruitRecord("b", "banana", 120);
        operationHandler.apply(fruitRecord);
        Integer actual = fruitQuantity.get("banana");
        Integer expected = 120;
        Assert.assertEquals(expected, actual);
    }

}
