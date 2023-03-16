package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        operationHandler = new ReturnOperationHandler();
        Storage.storage.put("banana", 20);
    }

    @Test
    public void return_OperationHandler_ok() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.RETURN,
                "banana", 50);
        operationHandler.apply(transactionDto);
        int expected = 70;
        int actual = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
