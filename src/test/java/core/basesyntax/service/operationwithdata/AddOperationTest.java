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
    private static final Integer QUANTITY = 12;
    private static final FruitDto FRUIT_DTO =
            new FruitDto(Operation.SUPPLY, FRUIT, QUANTITY);
    private static FruitOperationService addOperation;

    @BeforeClass
    public static void beforeClass() {
        addOperation = new AddOperation();
    }

    @Test
    public void apply_Ok() {
        addOperation.apply(FRUIT_DTO);
        addOperation.apply(FRUIT_DTO);
        Integer expected = 24;
        Integer actual = FruitStorage.fruitStorage.get(new Fruit(FRUIT));
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruitStorage.clear();
    }
}
