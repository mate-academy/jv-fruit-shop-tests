package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.dao.FruitDao;
import core.basesyntax.model.dao.FruitDaoImpl;
import core.basesyntax.model.dto.Fruit;
import core.basesyntax.service.interfaces.FruitService;
import core.basesyntax.service.interfaces.OperationHandler;
import core.basesyntax.storage.FruitStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
    private static final List<String> validData =
            List.of("type,fruit,quantity", "b,orange,25", "b,banana,100",
            "s,banana,25", "p,orange,13", "p,banana,115", "r,banana,15");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final FruitService fruitService = new FruitServiceImpl(new ReportGeneratorServiceImpl(),
            new OperationStrategyImpl(operationHandlerMap),
            new ParserServiceImpl());

    @BeforeClass
    public static void populateStrategyMap() {
        operationHandlerMap.put(Operation.BALANCE, new AddOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.SUPPLY, new AddOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.RETURN, new AddOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.PURCHASE, new RemoveOperationHandler(fruitDao));
    }

    @Test
    public void saveData_Ok() {
        Map<Fruit, Integer> expectedStorageMap = new HashMap<>();
        expectedStorageMap.put(new Fruit("orange"), 12);
        expectedStorageMap.put(new Fruit("banana"), 25);
        fruitService.saveData(validData);
        Map<Fruit, Integer> actualStorageMap = FruitStorage.getFruits();
        assertEquals(expectedStorageMap, actualStorageMap);
    }

    @Test
    public void getReport_Ok() {
        FruitStorage.getFruits().put(new Fruit("orange"), 173);
        FruitStorage.getFruits().put(new Fruit("banana"), 14);
        FruitStorage.getFruits().put(new Fruit("apple"), 231);
        String actualReport = fruitService.getReport();
        String expectedReport =
                "fruit,quantity" + System.lineSeparator()
                + "orange,173" + System.lineSeparator()
                + "banana,14" + System.lineSeparator()
                + "apple,231" + System.lineSeparator();
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void clearStorage() {
        FruitStorage.getFruits().clear();
    }
}
