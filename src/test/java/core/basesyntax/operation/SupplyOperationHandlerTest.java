package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction("p", new Fruit("banana"), 10);
    private static final Fruit fruit = new Fruit("banana");

    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        Storage.storage.put(fruit, 5);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        Storage.storage.put(fruit, 5);
        Integer expected = 15;
        supplyOperationHandler.apply(fruitTransaction);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
