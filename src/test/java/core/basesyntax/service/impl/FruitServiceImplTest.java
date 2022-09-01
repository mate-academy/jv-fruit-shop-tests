package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitDao fruitDao;
    private static Map<Fruit, Integer> test;
    private static FruitService fruitService;
    private static Map<FruitTransaction.Operation, OperationHandler> defaultMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        defaultMap = new HashMap<>();
        defaultMap.put(FruitTransaction.Operation.BALANCE,
                new FruitBalanceHandler(fruitDao));
        defaultMap.put(FruitTransaction.Operation.PURCHASE,
                new FruitPurchaseHandler(fruitDao));
        defaultMap.put(FruitTransaction.Operation.RETURN,
                new FruitReturnHandler(fruitDao));
        defaultMap.put(FruitTransaction.Operation.SUPPLY,
                new FruitSupplyHandler(fruitDao));
    }

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoTestImpl();
        test = new HashMap<>();
        fruitService = new FruitServiceImpl(defaultMap,fruitDao);
    }

    @Test
    public void createReport() {
        test.put(new Fruit("banana"), 152);
        test.put(new Fruit("apple"), 90);
        String expected = "fruits,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        String actual = fruitService.createReport();
        assertEquals(expected, actual);
    }

    private static class FruitDaoTestImpl implements FruitDao {
        @Override
        public void set(Fruit fruitData, int quantity) {
        }

        @Override
        public void add(Fruit fruit, int quantity) {
        }

        @Override
        public void subtract(Fruit fruitData, int quantity) {
        }

        @Override
        public int get(Fruit fruitName) {
            return 0;
        }

        @Override
        public Map<Fruit, Integer> getAll() {
            Map<Fruit, Integer> test = new HashMap<>();
            test.put(new Fruit("banana"), 152);
            test.put(new Fruit("apple"), 90);
            return test;
        }
    }
}
