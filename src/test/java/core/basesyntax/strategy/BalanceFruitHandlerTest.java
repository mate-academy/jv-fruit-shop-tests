package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceFruitHandlerTest {
    private static final String BANANA = "banana";
    private static final String OPERATION_BANANA = "b";
    private static final Integer VALID_VALUE = 10;
    private static final FruitTransaction TRANSACTION = new FruitTransaction(null, null, -1);
    private static FruitHandler fruitHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitHandler = new BalanceFruitHandler();
    }

    @Before
    public void setUp() {
        TRANSACTION.setOperation(null);
        TRANSACTION.setFruit(null);
        TRANSACTION.setValue(-1);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_fruitTransactionValid_ok() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        TRANSACTION.setFruit(BANANA);
        fruitHandler.apply(TRANSACTION);
        int expected = VALID_VALUE;
        int actual = Storage.storage.get(BANANA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_fruitInFruitTransactionNull_notOk() {
        TRANSACTION.setOperation(OPERATION_BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);
    }

    @Test(expected = RuntimeException.class)
    public void apply_operationInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setValue(VALID_VALUE);
        fruitHandler.apply(TRANSACTION);
    }

    @Test(expected = RuntimeException.class)
    public void apply_transactionInFruitTransactionNull_notOk() {
        fruitHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_valueInFruitTransactionNull_notOk() {
        TRANSACTION.setFruit(BANANA);
        TRANSACTION.setOperation(OPERATION_BANANA);
        fruitHandler.apply(TRANSACTION);
    }
}
