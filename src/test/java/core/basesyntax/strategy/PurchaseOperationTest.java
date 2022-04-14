package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final FruitRecordDto BANANA =
            new FruitRecordDto(Operation.PURCHASE,"banana", 10);
    private static final FruitRecordDto CITRON =
            new FruitRecordDto(Operation.PURCHASE, "citron", 100);
    private static final FruitRecordDto EMPTY =
            new FruitRecordDto(Operation.PURCHASE, "", 100);
    private static final FruitRecordDto CHECK =
            new FruitRecordDto(Operation.PURCHASE,"check", 50);
    private static final FruitRecordDto NEGATIVE_AMOUNT_APPLE =
            new FruitRecordDto(Operation.PURCHASE,"apple", -15);
    private static final FruitRecordDto APPLE =
            new FruitRecordDto(Operation.PURCHASE,"apple", 75);
    private static final FruitOperationHandler operationHandler = new PurchaseOperation();

    @Before
    public void setUp() {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("citron"), 200);
        Storage.fruitsDataBase.put(new Fruit("apple"), 23);
    }

    @Test
    public void apply_purchaseCorrectInput_Ok() {
        operationHandler.apply(BANANA);
        operationHandler.apply(CITRON);

        Map<Fruit, Integer> expected =
                Map.of(new Fruit("banana"), 0,
                new Fruit("citron"), 100,
                new Fruit("apple"), 23);
        Map<Fruit, Integer> actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseFruitNameEmpty_NotOk() {
        operationHandler.apply(EMPTY);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseUnknownFruit_NotOk() {
        operationHandler.apply(CHECK);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseNegativeFruitQuantity_NotOk() {
        operationHandler.apply(NEGATIVE_AMOUNT_APPLE);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMoreThenHave_NotOk() {
        operationHandler.apply(APPLE);
    }

    @After
    public void clear() {
        Storage.fruitsDataBase.clear();
    }
}
