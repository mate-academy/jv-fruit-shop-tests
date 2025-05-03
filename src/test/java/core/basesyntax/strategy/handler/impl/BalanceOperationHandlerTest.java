package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handle_addToStorageBalanceOperation_ok() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.BALANCE,
                "banana", 20);
        operationHandler.apply(transactionDto);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleBalance_addNegativeQuantityToStorage_notOk() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.BALANCE,
                "banana", -5);
        operationHandler.apply(transactionDto);

    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
