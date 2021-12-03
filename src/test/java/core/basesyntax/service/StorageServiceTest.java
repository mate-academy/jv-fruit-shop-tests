package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.activities.BalanceHandler;
import core.basesyntax.service.activities.Handler;
import core.basesyntax.service.activities.PurchaseHandler;
import core.basesyntax.service.activities.ReturnHandler;
import core.basesyntax.service.activities.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceTest {
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static StorageService service;
    private static Map<String, Handler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        CsvFileReader reader = new CsvFileReaderImpl("src/test/resources/storeInputData.csv");
        service = new StorageServiceImpl(reader);
    }

    @Before
    public void setUp() {
        strategyMap = new HashMap<>();
        strategyMap.put("b", new BalanceHandler());
        strategyMap.put("p", new PurchaseHandler());
        strategyMap.put("r", new ReturnHandler());
        strategyMap.put("s", new SupplyHandler());
    }

    @Test
    public void validate_StorageSize() {
        service.putDataToStorage(strategyMap);
        int actual = FruitStorage.fruits.size();
        assertEquals(2, actual);
    }

    @Test
    public void validate_DataInStorage() {
        service.putDataToStorage(strategyMap);
        String banana = FruitStorage.fruits.get(FIRST_INDEX).getFruitName();
        int bananaQuantity = FruitStorage.fruits.get(FIRST_INDEX).getFruitQuantity();
        assertEquals("banana", banana);
        assertEquals(152, bananaQuantity);
        String apple = FruitStorage.fruits.get(SECOND_INDEX).getFruitName();
        int appleQuantity = FruitStorage.fruits.get(SECOND_INDEX).getFruitQuantity();
        assertEquals("apple", apple);
        assertEquals(90, appleQuantity);
    }

    @After
    public void afterTest() {
        FruitStorage.fruits.clear();
    }
}
