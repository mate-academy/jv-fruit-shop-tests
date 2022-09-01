package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit("apple");
    }

    @Test
    public void applySupply_OK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        supplyOperationHandler.apply(new Transaction("s", new Fruit("apple"), 13));
        Integer expected = 27;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeSupplyValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        supplyOperationHandler.apply(new Transaction("s", new Fruit("apple"), -13));
        Integer expected = Integer.valueOf(27);
        Integer actual = Storage.storage.get(fruit);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullSupplyValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        supplyOperationHandler.apply(new Transaction("s", new Fruit("apple"), null));
    }
}
