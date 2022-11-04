package operations;

import static model.FruitTransaction.Operation.SUPPLY;
import static org.testng.Assert.assertEquals;

import dao.FruitImplemDao;
import db.Storage;
import model.FruitTransaction;
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
    public void handle_supplyTransaction_Ok() {
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
