package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddingHandlersTest {
    private static Handler addingHandlers;
    private static Storage storage;

    @Before
    public void setUp() {
        addingHandlers = new BalancesHandlerImpl();
    }

    @After
    public void tearDown() {
        Storage.storageDB.clear();
    }

    @Test(expected = RuntimeException.class)
    public void handle_addNegativeQuantity_notOk() {
        addingHandlers.handler(new FruitTransaction("b","test",-23));
    }

    @Test(expected = RuntimeException.class)
    public void handle_addNullFruitTransaction_notOk() {
        addingHandlers.handler(null);
    }

    @Test
    public void handle_checkSizeStorageDifferentKey_ok() {
        List<FruitTransaction> test = new ArrayList<>();
        test.add(new FruitTransaction("b","test1",12));
        test.add(new FruitTransaction("b","test2",12));
        test.add(new FruitTransaction("b","test3",12));
        test.add(new FruitTransaction("b","test4",12));
        for (FruitTransaction fruit : test) {
            addingHandlers.handler(fruit);
        }
        int expected = test.size();
        int actual = Storage.storageDB.size();
        assertEquals(expected,actual);
    }

    @Test
    public void handler_addSameKey_ok() {
        List<FruitTransaction> test = new ArrayList<>();
        test.add(new FruitTransaction("b","test",12));
        test.add(new FruitTransaction("b","test",12));
        test.add(new FruitTransaction("b","test",12));
        test.add(new FruitTransaction("b","test",12));
        for (FruitTransaction fruit : test) {
            addingHandlers.handler(fruit);
        }
        int expected = 1;
        int actual = Storage.storageDB.size();
        assertEquals(expected,actual);
    }

    @Test
    public void handler_contentCheck_ok() {
        addingHandlers.handler(new FruitTransaction("b","test1",23));
        addingHandlers.handler(new FruitTransaction("b","test2",54));
        int expected = 23;
        int actual = Storage.storageDB.get("test1");
        assertEquals(expected,actual);
        actual = Storage.storageDB.get("test2");
        expected = 54;
        assertEquals(expected,actual);
    }
}
