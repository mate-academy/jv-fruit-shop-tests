package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class SubtractOperationServiceImplTest {

    @Before
    public void setUp() throws Exception {
        Storage.clear();
        Storage.add(new Fruit("apple"), 20);
        Storage.add(new Fruit("banana"), 30);
        Storage.add(new Fruit("orange"), 33);
    }

    @Test
    public void subtractOperationServiceImpl_addOperationTest_Ok() {
        SubtractOperationServiceImpl subtractOperationService = new SubtractOperationServiceImpl();
        subtractOperationService.interact(new Transaction(new Fruit("apple"), 20, "p"));
        int expected = 0;
        int actual = Storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void subtractOperationServiceImpl_addFruitDoesNotExistTest_Ok() {
        SubtractOperationServiceImpl subtractOperationService = new SubtractOperationServiceImpl();
        subtractOperationService.interact(new Transaction(new Fruit("peach"), 20, "p"));
    }
}
