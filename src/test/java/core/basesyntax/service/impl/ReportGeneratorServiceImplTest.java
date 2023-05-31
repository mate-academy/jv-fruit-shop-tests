package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGeneratorService;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final Map<Fruit, Integer> storage = Storage.storage;
    private static final Fruit FRUIT_BANANA = new Fruit("banana");
    private static final Fruit FRUIT_APPLE = new Fruit("apple");
    private static final int DEFAULT_QUANTITY = 111;
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITER = ",";
    private static ReportGeneratorService reportGenerator;

    @Before
    public void beforeEach() {
        reportGenerator = new ReportGeneratorServiceImpl(new StorageDaoImpl());
        storage.put(FRUIT_BANANA, DEFAULT_QUANTITY);
    }

    @Test
    public void generateReport_oneFruitInStorage_ok() {
        String actual = reportGenerator.generateReport();
        String expected = String.format("%s%s",
                HEADER,
                System.lineSeparator() + FRUIT_BANANA.getName() + DELIMITER + DEFAULT_QUANTITY
        );
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_twoFruitsInStorage_ok() {
        storage.put(FRUIT_APPLE, DEFAULT_QUANTITY);
        String actual = reportGenerator.generateReport();
        String expected = String.format("%s%s%s",
                HEADER,
                System.lineSeparator() + FRUIT_BANANA.getName() + DELIMITER + DEFAULT_QUANTITY,
                System.lineSeparator() + FRUIT_APPLE.getName() + DELIMITER + DEFAULT_QUANTITY
        );
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_withEmptyStorage_ok() {
        storage.clear();
        String actual = reportGenerator.generateReport();
        String expected = HEADER;
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
