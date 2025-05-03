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

public class PurchaseHandlerTest {
    private static OperationHandler operationHandler;
    private FruitOperationDto validFruitOperationDto;
    private FruitOperationDto firstInvalidFruitOperationDto;
    private FruitOperationDto secondInvalidFruitOperationDto;
    private Fruit validFruit;
    private Fruit invalidFruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseHandler();
    }

    @Before
    public void setUp() {
        validFruit = new Fruit("banana");
        invalidFruit = new Fruit("apple");
        validFruitOperationDto = new FruitOperationDto(OperationType.PURCHASE, validFruit, 20);
        firstInvalidFruitOperationDto = new FruitOperationDto(
                OperationType.PURCHASE, validFruit, 100);
        secondInvalidFruitOperationDto = new FruitOperationDto(
                OperationType.PURCHASE, invalidFruit, 10);
        Storage.storage.put(validFruit, 50);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_purchaseWithValidData_Ok() {
        int expected = 30;
        int actual = operationHandler.apply(validFruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseWithInvalidQuantity_NotOk() {
        operationHandler.apply(firstInvalidFruitOperationDto);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseWithInvalidFruit_NotOk() {
        operationHandler.apply(secondInvalidFruitOperationDto);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseWithNull_NotOk() {
        operationHandler.apply(null);
    }
}
