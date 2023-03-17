package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int HUNDRED_QUANTITY = 100;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int ZERO_QUANTITY = 0;
    private static final int TEN_QUANTITY = 10;
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
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        Integer nullValue = null;
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(nullValue);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_notPresentInStorage_notOk() {
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(HUNDRED_QUANTITY);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test
    public void purchase_zeroValue_ok() {
        Storage.storage.put(FRUIT_NAME, TEN_QUANTITY);
        final Integer before = Storage.storage.get(FRUIT_NAME);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
        purchaseHandler.handle(fruitTransaction);
        Integer after = Storage.storage.get(FRUIT_NAME);
        assertEquals(before, after);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_negativeValue_notOk() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);;
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test
    public void purchase_validValue_ok() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(TEN_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseHandler.handle(fruitTransaction);
        Integer expected = HUNDRED_QUANTITY - TEN_QUANTITY;
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_moreThenHaveInStorage_notOk() {
        Storage.storage.put(FRUIT_NAME, TEN_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(HUNDRED_QUANTITY);
        purchaseHandler.handle(fruitTransaction);
    }
}
