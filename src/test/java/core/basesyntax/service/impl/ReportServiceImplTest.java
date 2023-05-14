package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static final String EMPTY_REPORT = "fruit,quantity";
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        FruitStorage.fruits.put("banana", 152);
        FruitStorage.fruits.put("apple", 90);
    }

    @Test
    public void createReport_ok() {
        String actual = reportService.report();
        assertEquals(EXPECTED_REPORT, actual);
    }

    @Test
    public void createReport_emptyStorage_notOk() {
        FruitStorage.fruits.clear();
        String actual = reportService.report();
        assertEquals(EMPTY_REPORT, actual);

    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
