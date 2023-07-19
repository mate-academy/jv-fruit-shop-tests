package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler returnHandler;
    private Fruit fruitKiwi;
    private Fruit fruitPie;

    @Before
    public void setUp() {
        Storage.storage.clear();
        returnHandler = new ReturnOperationHandler();
        fruitKiwi = new Fruit("kiwi");
        fruitPie = new Fruit("pie");
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void handle_ReturnCheck_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("r", fruitKiwi, 22);
        Storage.storage.put(fruitKiwi, 30);
        returnHandler.handle(fruitTransaction);
        Integer expected = 52;
        Integer actual = Storage.storage.get(fruitKiwi);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_ReturnAbsentFruitInStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("r", fruitPie, 10);
        returnHandler.handle(fruitTransaction);
    }
}
