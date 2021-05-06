package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitAddTest {
    private static final int AMOUNT = 12;
    private static final String NAME_FRUIT = "banana";
    private static Fruit fruit;
    private static FruitAdd fruitAdd;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() {
        fruitAdd = new FruitAdd();
        fruit = new Fruit(NAME_FRUIT);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void checkAddOperation_ok() {
        fruitRecordDto = new FruitRecordDto("b", fruit, AMOUNT);
        fruitAdd.operation(fruitRecordDto);
        fruitAdd.operation(fruitRecordDto);
        Assert.assertEquals(AMOUNT + AMOUNT, Storage.fruits.get(fruit).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void checkAddOperation_notOk() {
        fruitRecordDto = new FruitRecordDto("b", fruit, -AMOUNT);
        fruitAdd.operation(fruitRecordDto);
    }
}
