package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.StorageService;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileOperationImplTest {
    private static Map<String, FruitOperationHandler> handlerMap;
    private static StorageService storageService;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put("b", new BalanceOperation());
        handlerMap.put("p", new PurchaseOperation());
        handlerMap.put("r", new ReturnOperation());
        handlerMap.put("s", new SupplyOperation());
        storageService = new StorageServiceImpl();
        operationStrategy = new FileOperationImpl(handlerMap);
    }

    @Test
    public void getReport_Ok() {
        Storage.fruits.put(new Fruit("banana"),10);
        String expected = "Fruit, Quantity\n"
                + "banana,10\n";
        String actual = operationStrategy.getReport();
        assertEquals(actual.trim(), expected.trim());
    }
}
