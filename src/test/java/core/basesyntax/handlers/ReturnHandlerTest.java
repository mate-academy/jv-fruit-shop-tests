package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int HUNDRED_QUANTITY = 100;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int ZERO_QUANTITY = 0;
    private static final int TWENTY_QUANTITY = 20;
    private final OperationTypeHandler returnHandler = new ReturnHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void return_nullValue_notOk() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        Integer nullValue = null;
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(nullValue);
        returnHandler.handle(fruitTransaction);
    }

    @Test
    public void return_zeroValue_ok() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
        Integer expected = Storage.storage.get(fruitTransaction.getFruit());
        returnHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void return_negativeValue_notOk() {
        Storage.storage.put(FRUIT_NAME, TWENTY_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);
        returnHandler.handle(fruitTransaction);
    }

    @Test
    public void return_validValue_ok() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(TWENTY_QUANTITY);
        Integer expected = HUNDRED_QUANTITY + TWENTY_QUANTITY;
        returnHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(actual, expected);
    }
}
