package core.basesyntax.service.operationwithdata;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.fruitmodel.Fruit;
import core.basesyntax.operations.Operation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 50;
    private static final Integer WRONG_QUANTITY = -50;
    private static final FruitDto FRUIT_DTO =
            new FruitDto(Operation.SUPPLY, FRUIT, QUANTITY);
    private static FruitOperationService addOperation;

    @BeforeClass
    public static void beforeClass() {
        FruitStorage.fruitStorage.clear();
        addOperation = new AddOperation();
    }

    @Test
    public void apply_Ok() {
        addOperation.apply(FRUIT_DTO);
        addOperation.apply(FRUIT_DTO);
        Integer expected = 100;
        Integer actual = FruitStorage.fruitStorage.get(new Fruit(FRUIT));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_notOk() {
        addOperation.apply(new FruitDto(Operation.SUPPLY, FRUIT, WRONG_QUANTITY));
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruitStorage.clear();
    }
}
