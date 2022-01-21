package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.FruitStorage;
import core.basesyntax.service.amount.AdditionHandler;
import core.basesyntax.service.amount.AmountHandler;
import core.basesyntax.service.amount.SubtractionHandler;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubtractionHandlerTest {
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static final Map<String, Integer> FRUIT_COUNT = FruitStorage.FRUIT_COUNT;
    private static AmountHandler additionHandler;
    private static AmountHandler subtractionHandler;

    @Before
    public void setUp() {
        additionHandler = new AdditionHandler();
        subtractionHandler = new SubtractionHandler();
    }

    @Test
    public void subtractCorrectAmount_apply_OK() {
        FruitRecord newSupplyRecord = new FruitRecord(20,
                SUPPLY, new Fruit("banana"));
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        additionHandler.apply(newSupplyRecord);
        subtractionHandler.apply(newPurchaseRecord);
        int expected = 7;
        int actual = FRUIT_COUNT.get("banana");
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtractIncorrectAmount_apply_No_OK() {
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        subtractionHandler.apply(newPurchaseRecord);
    }

    @After
    public void afterEachTest() {
        FRUIT_COUNT.clear();
    }
}
