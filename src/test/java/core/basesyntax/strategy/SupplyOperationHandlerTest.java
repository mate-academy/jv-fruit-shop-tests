package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler supplyHandler;
    private Fruit fruitKiwi;

    @Before
    public void setUp() {
        Storage.storage.clear();
        supplyHandler = new SupplyOperationHandler();
        fruitKiwi = new Fruit("kiwi");
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void handle_SupplyCheck_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("s", fruitKiwi, 22);
        Storage.storage.put(fruitKiwi, 30);
        supplyHandler.handle(fruitTransaction);
        Integer expected = 52;
        Integer actual = Storage.storage.get(fruitKiwi);
        assertEquals(expected, actual);
    }

    @Test
    public void handle_SupplyNewFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("s", fruitKiwi, 25);
        supplyHandler.handle(fruitTransaction);
        Integer expected = 25;
        Integer actual = Storage.storage.get(fruitKiwi);
        assertEquals(expected, actual);
    }
}
