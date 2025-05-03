package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void apply_emptyStorage_isOk() {
        int expected = 50;
        int actual = balanceOperationHandler.apply(new FruitRecordDto("b", "apple", 50));
        Assert.assertEquals(expected, actual);
    }
}
