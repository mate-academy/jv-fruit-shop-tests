package core.basesyntax.service.operation;

import static core.basesyntax.db.Storage.fruits;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    private static PurchaseOperationHandler purchaseHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        fruits.put("peach", 73);
        fruits.put("pear", 27);
        fruitTransaction = FruitTransaction.of(PURCHASE, "peach", 12);
    }

    @Test
    public void handlePurchaseTransaction_Ok() {
        purchaseHandler.handle(fruitTransaction);
        int actual = fruits.get("peach");
        assertEquals(61, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
