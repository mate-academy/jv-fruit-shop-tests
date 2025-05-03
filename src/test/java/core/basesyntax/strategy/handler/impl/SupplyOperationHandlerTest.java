package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationHandler();
        Storage.storage.put("banana", 20);
    }

    @Test
    public void handle_addToStorageSupplyOperation_ok() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.SUPPLY,
                "banana", 100);
        operationHandler.apply(transactionDto);
        int actual = 120;
        int expected = Storage.storage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleSupply_addNegativeQuantityToStorage_notOk() {
        TransactionDto transactionDto = new TransactionDto(TransactionDto.Operation.SUPPLY,
                "banana", -10);
        operationHandler.apply(transactionDto);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
