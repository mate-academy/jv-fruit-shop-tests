package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String EXPECTED_REPORT = "fruit quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,40";

    private ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
        Storage.storage.put(APPLE, 40);
        Storage.storage.put(BANANA, 50);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void generateReport_validStorage_Ok() {
        String actualReport = reportGeneratorService.generateReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void generateReport_emptyStorage_notOk() {
        Storage.storage.clear();
        reportGeneratorService.generateReport();
    }
}
