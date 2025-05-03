package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
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
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitDao fruitDao;
    private static FruitService fruitService;
    private static Map<FruitTransaction.Operation, OperationHandler> defaultMap;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitService = new FruitServiceImpl(defaultMap, fruitDao);
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

    @Test
    public void createReport_isValid() {
        Storage.fruits.put(new Fruit("banana"), 152);
        Storage.fruits.put(new Fruit("apple"), 90);
        String expected = fruitService.createReport();
        String actual = "fruits,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
