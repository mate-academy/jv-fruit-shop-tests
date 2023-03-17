package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int HUNDRED_QUANTITY = 100;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int ZERO_QUANTITY = 0;
    private static final int TWENTY_QUANTITY = 20;
    private final OperationTypeHandler supplyHandler = new SupplyHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Before
    public void setUp() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void supply_nullValue_notOk() {
        Integer nullValue = null;
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(nullValue);
        supplyHandler.handle(fruitTransaction);
    }

    @Test
    public void supply_zeroValue_ok() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
        Integer expected = Storage.storage.get(fruitTransaction.getFruit());
        supplyHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void supply_negativeValue_notOk() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);
        supplyHandler.handle(fruitTransaction);
    }

    @Test
    public void supply_validValue_ok() {
        Storage.storage.put(FRUIT_NAME, HUNDRED_QUANTITY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(TWENTY_QUANTITY);
        Integer expected = HUNDRED_QUANTITY + TWENTY_QUANTITY;
        supplyHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(actual, expected);
    }
}
