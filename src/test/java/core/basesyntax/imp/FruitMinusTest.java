package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitMinusTest {
    private static final String NAME_FRUIT = "apple";
    private static final int AMOUNT = 1;
    private static Fruit fruit;
    private static FruitMinus fruitMinus;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() {
        fruitMinus = new FruitMinus();
        fruit = new Fruit(NAME_FRUIT);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void checkMinusOperation_ok() {
        fruitRecordDto = new FruitRecordDto("p", fruit, AMOUNT);
        FruitAdd fruitAdd = new FruitAdd();
        fruitAdd.operation(fruitRecordDto);
        fruitMinus.operation(fruitRecordDto);
        Assert.assertEquals(0, Storage.fruits.get(fruit).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void checkMinusOperatingWithWrongAmount_notOk() {
        fruitMinus.operation(null);
    }
}
