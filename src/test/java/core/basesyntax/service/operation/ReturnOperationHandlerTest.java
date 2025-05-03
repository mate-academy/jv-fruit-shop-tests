package core.basesyntax.service.operation;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeClass() {
        returnHandler = new ReturnOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        Storage.fruits.put("peach", 73);
        Storage.fruits.put("pear", 27);
    }

    @Test
    public void handle_returnTransaction_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(RETURN, "peach", 12);
        int expected = 85;
        returnHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get("peach");
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
