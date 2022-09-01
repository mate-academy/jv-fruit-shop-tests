package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        Storage.storage.put(new Fruit("banana"), 5);
        FruitTransaction fruitTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("banana"), 5);
        Integer expected = 15;
        supplyOperationHandler.apply(bananaTransaction);
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
