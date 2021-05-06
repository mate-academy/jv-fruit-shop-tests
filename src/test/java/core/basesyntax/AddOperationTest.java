package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.Operation;
import core.basesyntax.service.impl.AddOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static Operation operation;
    private int expected;
    private int actual;
    private FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() {
        operation = new AddOperation();
    }

    @Test
    public void addOperation_emptyStorage_OK() {
        fruitRecordDto = new FruitRecordDto(OperationType.BALANCE, "apple", 40);
        expected = 40;
        actual = operation.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addOperation_notEmptyStorage_OK() {
        Storage.fruits.put(new Fruit("apple"), 40);
        fruitRecordDto = new FruitRecordDto(OperationType.SUPPLY, "apple", 60);
        expected = 100;
        actual = operation.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addOperation_notEmptyStorageReturn_OK() {
        Storage.fruits.put(new Fruit("apple"), 60);
        fruitRecordDto = new FruitRecordDto(OperationType.RETURN, "apple", 40);
        expected = 100;
        actual = operation.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addOperation_fruitStorageAfterApply_OK() {
        final Fruit apple = new Fruit("apple");
        fruitRecordDto = new FruitRecordDto(OperationType.BALANCE, "apple", 50);
        operation.apply(fruitRecordDto);
        expected = 50;
        int actual = Storage.fruits.get(apple);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void classAfterEachTest() {
        Storage.fruits.clear();
    }
}
