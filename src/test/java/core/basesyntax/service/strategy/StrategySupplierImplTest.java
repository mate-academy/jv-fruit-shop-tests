package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategySupplierImplTest {
    private static StrategySupplier supplier;

    @BeforeClass
    public static void beforeClass() {
        supplier = new StrategySupplierImpl(new FruitDaoImpl());
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void getStrategyReduce() {
        String expected = ReduceQuantityStrategy.class.getName();
        String actual = supplier.getStrategy("p").getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void getStrategySet() {
        String expected = SetQuantityStrategy.class.getName();
        String actual = supplier.getStrategy("b").getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void getStrategyIncrease() {
        String expected = IncreaseQuantityStrategy.class.getName();
        String actual = supplier.getStrategy("s").getClass().getName();
        assertEquals(expected, actual);
    }
}
