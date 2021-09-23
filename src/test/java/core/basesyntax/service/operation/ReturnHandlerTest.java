package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static OperationHandler returnHandler;
    private static Fruit banana;
    private static Fruit apple;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnHandler = new ReturnHandler();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    @Before
    public void setUp() throws Exception {
        Storage.getFruitsStorage().put(banana, 100);
        Storage.getFruitsStorage().put(apple, 50);
    }

    @Test
    public void apply_ok() {
        expected = 120;
        actual = returnHandler.apply(new TransactionDto("p", banana, 20),
                Storage.getFruitsStorage());
        assertEquals(expected, actual);
        expected = 98;
        actual = returnHandler.apply(new TransactionDto("p", apple, 48),
                Storage.getFruitsStorage());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.getFruitsStorage().clear();
    }
}
