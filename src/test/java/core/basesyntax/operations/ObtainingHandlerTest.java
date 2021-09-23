package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.model.OperationType;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ObtainingHandlerTest {
    private static OperationHandler operationHandler;
    private FruitOperationDto fruitOperationDto;
    private Fruit fruit;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ObtainingHandler();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.SUPPLY, fruit, 20);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_obtainingWithEmptyStorage_Ok() {
        expected = 20;
        actual = operationHandler.apply(fruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_obtainingWithNotEmptyStorage_Ok() {
        FruitOperationDto newFruitOperationDto = new FruitOperationDto(
                OperationType.SUPPLY, fruit, 70);
        operationHandler.apply(fruitOperationDto);
        expected = 90;
        actual = operationHandler.apply(newFruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_obtainingWithNull_NotOk() {
        operationHandler.apply(null);
    }
}
