package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoSuchElementException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private final OperationHandler returnOperationHandler;
    private final FruitTransaction valid;
    private final FruitTransaction negative;
    private final FruitTransaction notAvailableFruit;

    public ReturnOperationHandlerTest() {
        returnOperationHandler = new ReturnOperationHandler();
        valid = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        negative = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", -1);
        notAvailableFruit = new FruitTransaction(FruitTransaction.Operation.RETURN, "lemon", 10);
    }

    @Before
    public void before() {
        Storage.storage.put("banana", 50);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void accept_validReturnTransaction_Ok() {
        returnOperationHandler.evaluateTransaction(valid);
        Assert.assertEquals(Integer.valueOf(60), Storage.storage.get("banana"));
    }

    @Test (expected = NullDataException.class)
    public void accept_null_NotOk() {
        returnOperationHandler.evaluateTransaction(null);
    }

    @Test (expected = TransactionQuantityException.class)
    public void accept_negativeReturnTransaction_NotOk() {
        returnOperationHandler.evaluateTransaction(negative);
    }

    @Test (expected = NoSuchElementException.class)
    public void accept_notAvailableFruitReturnTransaction_NotOk() {
        returnOperationHandler.evaluateTransaction(notAvailableFruit);
    }
}
