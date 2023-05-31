package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;
    private FruitTransaction fruitTransaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void apply_FruitTransactionIsValid_Ok() {
        fruitTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        fruit = new Fruit("banana");
        Storage.storage.put(fruit, 5);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_FruitTransactionIsNull_NotOk() {
        supplyOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
