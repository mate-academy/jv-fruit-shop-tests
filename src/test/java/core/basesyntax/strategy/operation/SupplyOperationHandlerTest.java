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

public class SupplyOperationHandlerTest {
    private final OperationHandler supplyOperationHandler;
    private final FruitTransaction valid;
    private final FruitTransaction negative;
    private final FruitTransaction notAvailableFruit;

    public SupplyOperationHandlerTest() {
        supplyOperationHandler = new SupplyOperationHandler();
        valid = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        negative = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -1);
        notAvailableFruit = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "lemon", 10);
    }

    @Before
    public void before() {
        Storage.storage.put("banana", 50);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test(expected = NullDataException.class)
    public void accept_null_NotOk() {
        supplyOperationHandler.evaluateTransaction(null);
    }

    @Test
    public void accept_validSupplyTransaction_Ok() {
        supplyOperationHandler.evaluateTransaction(valid);
        Assert.assertEquals(Integer.valueOf(60), Storage.storage.get("banana"));
    }

    @Test (expected = TransactionQuantityException.class)
    public void accept_negativeSupplyTransaction_NotOk() {
        supplyOperationHandler.evaluateTransaction(negative);
    }

    @Test (expected = NoSuchElementException.class)
    public void accept_notAvailableFruitSupplyTransaction_NotOk() {
        supplyOperationHandler.evaluateTransaction(notAvailableFruit);
    }
}
