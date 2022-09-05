package core.basesyntax.service.operation;

import static core.basesyntax.db.Storage.fruits;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        returnHandler = new ReturnOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        fruits.put("peach", 73);
        fruits.put("pear", 27);
        fruitTransaction = FruitTransaction.of(RETURN, "peach", 12);
    }

    @Test
    public void handleReturnTransaction_Ok() {
        returnHandler.handle(fruitTransaction);
        int actual = fruits.get("peach");
        assertEquals(85, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
