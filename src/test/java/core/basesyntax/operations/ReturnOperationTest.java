package core.basesyntax.operations;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeClass() {
        returnHandler = new ReturnOperation(new FruitImplemDao());
    }

    @Before
    public void setUp() {
        Storage.fruits.put("peach", 73);
        Storage.fruits.put("pear", 27);
    }

    @Test
    public void handleReturnTransaction_ok() {
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
