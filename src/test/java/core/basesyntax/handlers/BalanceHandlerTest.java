package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
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
        Storage.storage.put("orange", 100);
        Integer nullValue = null;
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(nullValue);
        balanceHandler.handle(fruitTransaction);
    }

    @Test
    public void balance_zeroValue_ok() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(0);
        Integer expected = fruitTransaction.getQuantity();
        balanceHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test
    public void balance_validValue_ok() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        Integer expected = fruitTransaction.getQuantity();
        balanceHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void balance_negativeValue_notOk() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(-10);
        balanceHandler.handle(fruitTransaction);
    }
}
