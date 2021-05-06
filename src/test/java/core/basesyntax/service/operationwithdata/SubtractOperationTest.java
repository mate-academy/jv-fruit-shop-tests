package core.basesyntax.service.operationwithdata;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.fruitmodel.Fruit;
import core.basesyntax.operations.Operation;
import org.junit.AfterClass;
import org.junit.Test;

public class SubtractOperationTest {
    private static final String FRUIT_NAME = "cherry";
    private static final Integer QUANTITY = 45;
    private static final Integer SUBTRACT = 5;
    private static FruitDto fruitDto =
            new FruitDto(Operation.PURCHASE, FRUIT_NAME, SUBTRACT);
    private static FruitOperationService subtractOperation =
            new SubtractOperation();

    @Test
    public void apply_Ok() {
        FruitStorage.fruitStorage.put(new Fruit(FRUIT_NAME), QUANTITY);
        int excepted = 40;
        int actual = subtractOperation.apply(fruitDto);
        assertEquals(excepted, actual);
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruitStorage.clear();
    }
}

