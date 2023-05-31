package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationServiceImplTest {
    private static OperationService addOperationService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        addOperationService = new AddOperationServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.clear();
        Storage.add(new Fruit("apple"), 20);
        Storage.add(new Fruit("banana"), 30);
        Storage.add(new Fruit("orange"), 33);
    }

    @Test
    public void addOperationServiceImpl_addOperationTest_Ok() {
        addOperationService.interact(new Transaction(new Fruit("apple"), 20, "s"));
        int expected = 40;
        int actual = Storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void addOperationServiceImpl_addFruitDoesNotExistTest_Ok() {
        addOperationService.interact(new Transaction(new Fruit("peach"), 50, "s"));
        int expected = 50;
        int actual = Storage.get(new Fruit("peach"));
        assertEquals(expected, actual);
    }
}
