package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String BANANA_KEY = "banana";
    private static final Integer BANANA_VALUE = 50;
    private static final String VALID_REPORT_DATA = "banana,50";
    private static final String EXPECTED_LINE = "fruit,quantity" + System.lineSeparator();

    private static ReportGeneratorService reportGeneratorService;

    @BeforeClass
    public static void beforeClass() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void generateReport_emptyDataBase_ok() {
        String actual = reportGeneratorService.generateReport();
        assertEquals(EXPECTED_LINE, actual);
    }

    @Test
    public void generateReport_validDataBase_ok() {
        FruitStorage.fruitStorage.put(BANANA_KEY, BANANA_VALUE);
        String actual = reportGeneratorService.generateReport();
        String expected = EXPECTED_LINE + VALID_REPORT_DATA;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
