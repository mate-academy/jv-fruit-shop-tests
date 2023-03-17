package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class PurchaseFruitHandlerTest {
    private static final String BANANA = "banana";
    private static final String OPERATION_BANANA = "s";
    private static final Integer VALID_VALUE = 10;
    private static final Integer BALANCE_VALUE = 50;
    private static final FruitTransaction TRANSACTION = new FruitTransaction(null, null, -1);
    private static FruitHandler fruitHandler;

    @Before
    public void setUp() {
        fruitHandler = new PurchaseFruitHandler();
        TRANSACTION.setOperation(null);
        TRANSACTION.setFruit(null);
        TRANSACTION.setValue(-1);
    }

    @Test
    public void fruitTransactionValid_ok() {
        Storage.storage.put(BANANA, BALANCE_VALUE);
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        TRANSACTION.setFruit(BANANA);
        fruitHandler.apply(TRANSACTION);
        int expected = (int) BALANCE_VALUE - VALID_VALUE;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fruitInFruitTransactionNull_notOk() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);
    }

    @Test (expected = RuntimeException.class)
    public void operationInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);
    }

    @Test (expected = RuntimeException.class)
    public void valueInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setOperation(OPERATION_BANANA);
        fruitHandler.apply(TRANSACTION);
    }
}
