package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final char SEPARATOR = ',';
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static ReportMakerService service;

    @BeforeClass
    public static void setUp() {
        service = new ReportMakerServiceImpl();
        Storage.storage.put(BANANA, BANANA_QUANTITY);
        Storage.storage.put(APPLE, APPLE_QUANTITY);
    }

    @Test
    public void makeReport_ok() {
        StringBuilder expected = new StringBuilder(HEADER);
        expected.append(System.lineSeparator());
        expected.append(BANANA).append(SEPARATOR);
        expected.append(BANANA_QUANTITY).append(System.lineSeparator());
        expected.append(APPLE).append(SEPARATOR);
        expected.append(APPLE_QUANTITY);
        String actual = service.makeReport(Storage.storage);
        assertEquals("Expected value: " + expected + " but was " + actual,
                expected.toString(), actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
