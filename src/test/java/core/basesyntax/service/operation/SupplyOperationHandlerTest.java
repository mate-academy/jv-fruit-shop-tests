package core.basesyntax.service.operation;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        supplyHandler = new SupplyOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        Storage.fruits.put("apple", 320);
        Storage.fruits.put("grape", 180);
        fruitTransaction = FruitTransaction.of(SUPPLY, "apple", 120);
    }

    @Test
    public void handle_supplyTransaction_Ok() {
        int expected = 440;
        supplyHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
