package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler returnOperation;
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        returnOperation = new SupplyOperationHandler();
        fruit = new Fruit("banana");
        Storage.storage.put(fruit, 100);
    }

    @Test
    public void apply_Ok() {
        returnOperation.apply(new FruitTransaction("s", fruit, 51));
        Integer expected = 151;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullValue_NotOk() {
        returnOperation.apply(new FruitTransaction("s", fruit, null));
    }

    @Test(expected = NullPointerException.class)
    public void apply_Null_NotOk() {
        returnOperation.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
