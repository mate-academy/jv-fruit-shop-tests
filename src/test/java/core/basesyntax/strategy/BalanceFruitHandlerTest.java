package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceFruitHandlerTest {
    private static final String BANANA = "banana";
    private static final String OPERATION_BANANA = "b";
    private static final Integer VALID_VALUE = 10;
    private static final FruitTransaction TRANSACTION = new FruitTransaction(null, null, -1);
    private static FruitHandler fruitHandler;

    @Before
    public void setUp() {
        fruitHandler = new BalanceFruitHandler();
        TRANSACTION.setOperation(null);
        TRANSACTION.setFruit(null);
        TRANSACTION.setValue(-1);
    }

    @Test
    public void fruitTransactionValid_ok() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        TRANSACTION.setFruit(BANANA);
        fruitHandler.apply(TRANSACTION);
        int expected = (int) VALID_VALUE;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fruitInFruitTransactionNull_notOk() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);

    }

    @Test(expected = RuntimeException.class)
    public void operationInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);
    }

    @Test(expected = RuntimeException.class)
    public void valueInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setOperation(OPERATION_BANANA);
        fruitHandler.apply(TRANSACTION);
    }
}
