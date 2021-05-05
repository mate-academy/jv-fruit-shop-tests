package core.basesyntax;

import static core.basesyntax.service.impl.Operation.BALANCE;
import static core.basesyntax.service.impl.Operation.RETURN;
import static core.basesyntax.service.impl.Operation.SUPPLY;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.impl.AddOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static FruitOperationHandler fruitOperationHandler;
    private int expected;
    private int actual;
    private FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void initialization() {
        fruitOperationHandler = new AddOperation();
    }

    @Test
    public void applyOperation_emptyStorage_ok() {
        fruitRecordDto = new FruitRecordDto(BALANCE, "apple", 100);
        expected = 100;
        actual = fruitOperationHandler.applyOperation(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_notEmptyStorageSupply_ok() {
        Storage.fruits.put(new Fruit("apple"), 50);
        fruitRecordDto = new FruitRecordDto(SUPPLY, "apple", 100);
        expected = 150;
        actual = fruitOperationHandler.applyOperation(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_notEmptyStorageReturn_ok() {
        Storage.fruits.put(new Fruit("apple"), 60);
        fruitRecordDto = new FruitRecordDto(RETURN, "apple", 70);
        expected = 130;
        actual = fruitOperationHandler.applyOperation(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_fruitPutToStorageAfterApplying_ok() {
        final Fruit apple = new Fruit("apple");
        fruitRecordDto = new FruitRecordDto(BALANCE, "apple", 100);
        fruitOperationHandler.applyOperation(fruitRecordDto);
        expected = 100;
        int quantityInStorage = Storage.fruits.get(apple);
        Assert.assertEquals(expected, quantityInStorage);
    }

    @After
    public void clearAfterEachTest() {
        Storage.fruits.clear();
    }
}
