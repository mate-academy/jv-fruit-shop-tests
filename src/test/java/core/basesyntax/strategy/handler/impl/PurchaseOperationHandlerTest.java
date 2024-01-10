package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        operationHandler = new PurchaseOperationHandler();
        Storage.storage.put("banana", 120);
    }

    @Test
    public void handle_addToStoragePurchaseOperation_ok() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.PURCHASE,
                "banana", 13);
        operationHandler.apply(transactionDto);
        int actual = 107;
        int expected = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_withEmptyStorage_notOk() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.PURCHASE,
                "banana", 130);
        operationHandler.apply(transactionDto);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
