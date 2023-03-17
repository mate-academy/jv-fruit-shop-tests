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
    public void setUp() {
        operationHandler = new ReturnOperationHandler();
        Storage.storage.put("banana", 20);
    }

    @Test
    public void handle_addToStorageReturnOperation_ok() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.RETURN,
                "banana", 50);
        operationHandler.apply(transactionDto);
        int expected = 70;
        int actual = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleReturn_addNegativeQuantityToStorage_notOk() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.RETURN,
                "banana", -20);
        operationHandler.apply(transactionDto);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
