package core.basesyntax.operations;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyHandler = new SupplyOperation(new FruitImplemDao());
    }

    @Before
    public void setUp() {
        Storage.fruits.put("apple", 320);
        Storage.fruits.put("grape", 180);
    }

    @Test
    public void handleSupplyTransaction_ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(SUPPLY, "apple", 120);
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
