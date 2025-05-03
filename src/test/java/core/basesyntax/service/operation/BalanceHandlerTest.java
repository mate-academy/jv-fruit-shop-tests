package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;
    private static Fruit banana;
    private static Fruit apple;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceHandler = new BalanceHandler();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    @Before
    public void setUp() throws Exception {
        Storage.getFruitsStorage().put(banana, 10);
        Storage.getFruitsStorage().put(apple, 5);
    }

    @Test
    public void apply_ok() {
        expected = 10;
        actual = balanceHandler.apply(new TransactionDto("b", banana, 10),
                Storage.getFruitsStorage());
        assertEquals(expected, actual);
        expected = 15;
        actual = balanceHandler.apply(new TransactionDto("b", apple, 15),
                Storage.getFruitsStorage());
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruitsStorage().clear();
    }
}
