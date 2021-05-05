package core.basesyntax.service.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RemoveOperationStrategyTest {
    private static FruitDao fruitDao;
    private static RecordHandler addOperation;
    private static RecordHandler removeOperation;
    private static Fruit fruit;
    private static FruitRecordDto firstFruitRecordDto;
    private static FruitRecordDto secondFruitRecordDto;
    private static final int FIRST_POSITIVE = 345;
    private static final int SECOND_POSITIVE = 275;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        addOperation = new AddOperationStrategy(fruitDao);
        removeOperation = new RemoveOperationStrategy(fruitDao);
        fruit = new Fruit("Orange");
    }

    @Test
    public void check_removeOperation_OK() {
        firstFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("p"), fruit, FIRST_POSITIVE);
        secondFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("p"), fruit, SECOND_POSITIVE);
        addOperation.applyAction(firstFruitRecordDto);
        addOperation.applyAction(secondFruitRecordDto);
        removeOperation.applyAction(secondFruitRecordDto);
        Assert.assertEquals(FIRST_POSITIVE, Storage.fruits.get(fruit).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void check_removeOperation_Not_OK() {
        firstFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("p"), fruit, -FIRST_POSITIVE);
        removeOperation.applyAction(firstFruitRecordDto);
    }
}
