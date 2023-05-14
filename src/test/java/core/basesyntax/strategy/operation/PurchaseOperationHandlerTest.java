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

public class PurchaseOperationHandlerTest {
    private final OperationHandler purchaseOperationHandler;
    private final FruitTransaction valid;
    private final FruitTransaction negative;
    private final FruitTransaction moreThanSupply;
    private final FruitTransaction notAvailableFruit;

    public PurchaseOperationHandlerTest() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        valid = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        negative = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -1);
        moreThanSupply = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 51);
        notAvailableFruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "lemon", 10);
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
    public void accept_validPurchaseTransaction_Ok() {
        purchaseOperationHandler.evaluateTransaction(valid);
        Assert.assertEquals(Integer.valueOf(40), Storage.storage.get("banana"));
    }

    @Test (expected = NullDataException.class)
    public void accept_null_NotOk() {
        purchaseOperationHandler.evaluateTransaction(null);
    }

    @Test (expected = TransactionQuantityException.class)
    public void accept_moreThanSupplyPurchaseTransaction_NotOk() {
        purchaseOperationHandler.evaluateTransaction(moreThanSupply);
    }

    @Test (expected = TransactionQuantityException.class)
    public void accept_negativePurchaseTransaction_NotOk() {
        purchaseOperationHandler.evaluateTransaction(negative);
    }

    @Test (expected = NoSuchElementException.class)
    public void accept_notAvailableFruitPurchaseTransaction_NotOk() {
        purchaseOperationHandler.evaluateTransaction(notAvailableFruit);
    }
}
