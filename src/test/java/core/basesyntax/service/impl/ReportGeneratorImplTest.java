package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
    private String expected;

    @Before
    public void setUp() throws Exception {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        Fruit orange = new Fruit("orange");
        Storage.storage.put(banana, 10);
        Storage.storage.put(apple, 122);
        Storage.storage.put(orange, 1122);
        StringBuilder stringBuilder = new StringBuilder(HEADER);
        Storage.storage.forEach((key, value) -> stringBuilder.append(key.getName())
                .append(",")
                .append(value)
                .append(System.lineSeparator()));
        expected = stringBuilder.toString().trim();
    }

    @Test
    public void createDataForReport_Ok() {
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}
