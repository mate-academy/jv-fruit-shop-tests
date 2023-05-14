package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int VALID_QUANTITY = 100;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int ZERO_QUANTITY = 0;
    private final OperationTypeHandler balanceHandler = new BalanceHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void balance_nullValue_notOk() {
        Storage.storage.put(FRUIT_NAME, VALID_QUANTITY);
        Integer nullValue = null;
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(nullValue);
        balanceHandler.handle(fruitTransaction);
    }

    @Test
    public void balance_zeroValue_ok() {
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
        Integer expected = fruitTransaction.getQuantity();
        balanceHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void balance_validValue_ok() {
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(VALID_QUANTITY);
        Integer expected = fruitTransaction.getQuantity();
        balanceHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balance_negativeValue_notOk() {
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);
        balanceHandler.handle(fruitTransaction);
    }
}
