package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrect1 =
            new FruitRecordDto(Operation.PURCHASE,"banana", 10);
    private static final FruitRecordDto fruitRecordDtoCorrect2 =
            new FruitRecordDto(Operation.PURCHASE, "mango", 100);
    private static final FruitRecordDto fruitRecordDtoFruitNameEmpty =
            new FruitRecordDto(Operation.PURCHASE, "", 100);
    private static final FruitRecordDto fruitRecordDtoUnknownFruit =
            new FruitRecordDto(Operation.PURCHASE,"qqqq", 100);
    private static final FruitRecordDto fruitRecordDtoNegativeQuantity =
            new FruitRecordDto(Operation.PURCHASE,"apple", -20);
    private static final FruitRecordDto fruitRecordDtoMoreQuantity =
            new FruitRecordDto(Operation.PURCHASE,"apple", 50);
    private static FruitOperationHandler operationHandler;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseOperation();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("mango"), 200);
        Storage.fruitsDataBase.put(new Fruit("apple"), 30);
    }

    @Test
    public void apply_purchaseCorrectCase_Ok() {
        operationHandler.apply(fruitRecordDtoCorrect1);
        operationHandler.apply(fruitRecordDtoCorrect2);

        expected = Map.of(new Fruit("banana"), 0,
                new Fruit("mango"), 100,
                new Fruit("apple"), 30);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_purchaseFruitNameEmpty_Ok() {
        operationHandler.apply(fruitRecordDtoFruitNameEmpty);
    }

    @Test (expected = RuntimeException.class)
    public void apply_purchaseUnknownFruit_Ok() {
        operationHandler.apply(fruitRecordDtoUnknownFruit);
    }

    @Test (expected = RuntimeException.class)
    public void apply_purchaseNegativeFruitQuantity_Ok() {
        operationHandler.apply(fruitRecordDtoNegativeQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void apply_purchaseMoreThenHave_Ok() {
        operationHandler.apply(fruitRecordDtoMoreQuantity);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
