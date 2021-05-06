package core.basesyntax.service.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationStrategyTest {
    private static FruitDao fruitDao;
    private static RecordHandler addOperation;
    private static Fruit fruit;
    private static FruitRecordDto firstFruitRecordDto;
    private static FruitRecordDto secondFruitRecordDto;
    private static final int FIRST_POSITIVE = 345;
    private static final int SECOND_POSITIVE = 275;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        addOperation = new AddOperationStrategy(fruitDao);
        fruit = new Fruit("Grapes");
    }

    @Test
    public void check_addOperation_OK() {
        firstFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("b"), fruit, FIRST_POSITIVE);
        secondFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("b"), fruit, SECOND_POSITIVE);
        addOperation.applyAction(firstFruitRecordDto);
        addOperation.applyAction(secondFruitRecordDto);
        int expected = 620;
        Assert.assertEquals(expected, Storage.fruits.get(fruit).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void check_addOperation_Not_OK() {
        firstFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("r"), fruit, -FIRST_POSITIVE);
        secondFruitRecordDto =
                new FruitRecordDto(Operation.getOperationByLetter("r"), fruit, SECOND_POSITIVE);
        addOperation.applyAction(firstFruitRecordDto);
        addOperation.applyAction(secondFruitRecordDto);
    }
}
