package core.basesyntax.db.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.service.ReportCreatorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static final String TEST_KEY = "testKey";
    private static final int TEST_VALUE = 1;
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void setUp() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void getReport_validData_ok() {
        String expected = ReportCreatorServiceImpl.HEAD + System.lineSeparator()
                + TEST_KEY + "," + TEST_VALUE + System.lineSeparator();
        Storage.getAll().put(TEST_KEY, TEST_VALUE);
        String actual = reportCreatorService.getReport(Storage.getAll());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getAll().clear();
    }
}
