package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static core.basesyntax.db.Storage.fruits;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        supplyHandler = new SupplyOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        fruits.put("apple", 320);
        fruits.put("grape", 180);
        fruitTransaction = FruitTransaction.of(SUPPLY, "apple", 120);
    }

    @Test
    public void handleSupplyTransaction_Ok() {
        supplyHandler.handle(fruitTransaction);
        int actual = fruits.get("apple");
        assertEquals(440, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
