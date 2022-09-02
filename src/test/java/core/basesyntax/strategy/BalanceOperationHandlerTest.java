package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceOperation;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        balanceOperation = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction("b", new Fruit("banana"), 100);
    }

    @Test
    public void getBalance_Ok() {
        Integer expected = fruitTransaction.getQuantity();
        balanceOperation.apply(fruitTransaction);
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test
    public void getBalanceFromNullebleFruit_Ok() {
        FruitTransaction emptyFruit = new FruitTransaction("b", null, 5);
        Integer expected = 5;
        balanceOperation.apply(emptyFruit);
        Integer actual = Storage.storage.get(null);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_Null_NotOk() {
        balanceOperation.apply(null);
    }
}
