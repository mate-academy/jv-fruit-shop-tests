package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseQuantityStrategyTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        strategy = new IncreaseQuantityStrategy(new FruitDaoImpl());
    }

    @Test
    public void test_increaseProcess_Ok() {
        Fruit fruit = new Fruit("cherry");
        Storage.fruitStorage.put(fruit, 25000);
        strategy.process(new FruitDto("s", "cherry", 77));
        Integer expected = 25077;
        Integer actual = Storage.fruitStorage.get(fruit);
        assertEquals(expected, actual);
        Storage.fruitStorage.clear();
    }
}
