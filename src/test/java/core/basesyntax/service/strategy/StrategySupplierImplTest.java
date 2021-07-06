package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategySupplierImplTest {
    private static StrategySupplier supplier;

    @BeforeClass
    public static void beforeClass() {
        supplier = new StrategySupplierImpl(new FruitDaoImpl());
    }

    @Test
    public void test_getStrategy() {
        String expected = ReduceQuantityStrategy.class.getName();
        String actual = supplier.getStrategy("p").getClass().getName();
        assertEquals(expected, actual);
        Storage.fruitStorage.clear();
    }
}
