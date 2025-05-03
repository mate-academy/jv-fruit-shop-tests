package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.FruitOperationHandler;
import core.basesyntax.handlers.impl.AddOperation;
import core.basesyntax.handlers.impl.RemoveOperation;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dtos.FruitDtoTransaction;
import core.basesyntax.services.interfaces.FruitShopService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;

    @Before
    public void setUp() {
        Map<OperationType, FruitOperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new AddOperation());
        operationHandlerMap.put(OperationType.SUPPLY, new AddOperation());
        operationHandlerMap.put(OperationType.PURCHASE, new RemoveOperation());
        operationHandlerMap.put(OperationType.RETURN, new AddOperation());
        fruitShopService = new FruitShopServiceImpl(operationHandlerMap);
    }

    @After
    public void afterEachTest() {
        Storage.getFruits().clear();
    }

    @Test
    public void test_applyTransaction_Ok() {
        Fruit fruit = new Fruit("pineapple");
        List<FruitDtoTransaction> fruitDtoTransactions = List.of(new FruitDtoTransaction(
                OperationType.BALANCE, fruit.getName(), 40),
                new FruitDtoTransaction(OperationType.PURCHASE, fruit.getName(), 10));
        fruitShopService.applyTransactions(fruitDtoTransactions);
        Integer actual = Storage.getFruits().get(fruit);
        Integer expected = 30;
        assertEquals(expected, actual);
    }

    @Test
    public void test_creatingReport_Ok() {
        Storage.getFruits().put(new Fruit("banana"), 30);
        Storage.getFruits().put(new Fruit("orange"), 35);
        String actual = fruitShopService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,30"
                + System.lineSeparator() + "orange,35" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
