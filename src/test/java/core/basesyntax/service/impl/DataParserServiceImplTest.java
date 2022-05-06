package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserService dataParserService;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void parseData_validData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("b,apple,100");
        ArrayList<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
