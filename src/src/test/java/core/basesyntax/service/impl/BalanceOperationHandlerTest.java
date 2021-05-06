package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void apply_emptyStorage_isOk() {
        int expected = 50;
        int actual = balanceOperationHandler.apply(new FruitRecordDto("b", "apple", 50));
        Assert.assertEquals(expected, actual);
    }
}
