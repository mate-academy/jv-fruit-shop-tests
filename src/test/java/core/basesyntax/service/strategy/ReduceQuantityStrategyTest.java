package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReduceQuantityStrategyTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        strategy = new ReduceQuantityStrategy(new FruitDaoImpl());
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void reduceProcess_Ok() {
        Fruit fruit = new Fruit("cherry");
        Storage.fruitStorage.put(fruit, 25000);
        strategy.process(new FruitDto("p", "cherry", 77));
        Integer expected = 24923;
        Integer actual = Storage.fruitStorage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void reduceProcess_NotOk() {
        try {
            Fruit fruit = new Fruit("cherry");
            Storage.fruitStorage.put(fruit, 25000);
            strategy.process(new FruitDto("p", "cherry", 25077));
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }
}
