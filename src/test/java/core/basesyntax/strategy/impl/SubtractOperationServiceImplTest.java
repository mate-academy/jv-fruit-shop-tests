package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationService;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationServiceImplTest {
    private static OperationService subtractOperationService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        subtractOperationService = new SubtractOperationServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.clear();
        Storage.add(new Fruit("apple"), 20);
        Storage.add(new Fruit("banana"), 30);
        Storage.add(new Fruit("orange"), 33);
    }

    @Test
    public void subtractOperationServiceImpl_addOperationTest_Ok() {
        subtractOperationService.interact(new Transaction(new Fruit("apple"), 20, "p"));
        int expected = 0;
        int actual = Storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void subtractOperationServiceImpl_addFruitDoesNotExistTest_Ok() {
        subtractOperationService.interact(new Transaction(new Fruit("peach"), 20, "p"));
    }
}
