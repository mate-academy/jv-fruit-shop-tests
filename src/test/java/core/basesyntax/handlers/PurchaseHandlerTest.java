package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private final OperationTypeHandler purchaseHandler = new PurchaseHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void purchase_nullValue_notOk() {
        Storage.storage.put("orange", 100);
        Integer nullValue = null;
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(nullValue);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_notPresentInStorage_notOk() {
        fruitTransaction.setFruit("absent");
        fruitTransaction.setQuantity(10);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test
    public void purchase_zeroValue_ok() {
        Storage.storage.put("zero",10);
        final Integer before = Storage.storage.get("zero");
        fruitTransaction.setFruit("zero");
        fruitTransaction.setQuantity(0);
        purchaseHandler.handle(fruitTransaction);
        Integer after = Storage.storage.get("zero");
        assertEquals(before, after);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_negativeValue_notOk() {
        Storage.storage.put("negative", 20);;
        fruitTransaction.setQuantity(-10);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test
    public void purchase_validValue_ok() {
        Storage.storage.put("valid", 100);
        fruitTransaction.setFruit("valid");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseHandler.handle(fruitTransaction);
        Integer expected = 90;
        Integer actual = Storage.storage.get("valid");
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_moreThenHaveInStorage_notOk() {
        Storage.storage.put("less", 10);
        fruitTransaction.setFruit("less");
        fruitTransaction.setQuantity(100);
        purchaseHandler.handle(fruitTransaction);
    }
}
