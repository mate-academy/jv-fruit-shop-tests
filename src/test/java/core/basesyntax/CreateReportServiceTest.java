package core.basesyntax;

import static core.basesyntax.db.FruitStorage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import org.junit.After;
import org.junit.Test;

public class CreateReportServiceTest {
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String REPORT_SPLITTER = ",";
    private static final String REPORT_SEPARATOR = System.lineSeparator();
    private static final String TEST_FRUIT_NAME_APPLE = "apple";
    private static final int TEST_QUANTITY_20 = 20;
    private static final String TEST_FRUIT_NAME_BANANA = "banana";
    private static final int TEST_QUANTITY_40 = 40;
    private final CreateReportService createReportService = new CreateReportServiceImpl();

    @Test
    public void createReport_correctData_Ok() {
        storage.put(TEST_FRUIT_NAME_APPLE, TEST_QUANTITY_20);
        String expected = REPORT_HEAD + REPORT_SEPARATOR + TEST_FRUIT_NAME_APPLE
                + REPORT_SPLITTER + TEST_QUANTITY_20 + REPORT_SEPARATOR;
        String actual = createReportService.createReport(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_multiplyData_Ok() {
        storage.put(TEST_FRUIT_NAME_APPLE, TEST_QUANTITY_20);
        storage.put(TEST_FRUIT_NAME_BANANA, TEST_QUANTITY_40);
        String expected = REPORT_HEAD + REPORT_SEPARATOR + TEST_FRUIT_NAME_BANANA
                + REPORT_SPLITTER + TEST_QUANTITY_40 + REPORT_SEPARATOR
                + TEST_FRUIT_NAME_APPLE + REPORT_SPLITTER
                + TEST_QUANTITY_20 + REPORT_SEPARATOR;
        String actual = createReportService.createReport(storage);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullData_notOk() {
        createReportService.createReport(null);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
