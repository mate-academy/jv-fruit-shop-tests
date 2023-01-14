package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String STORAGE_KEY = "justKey";
    private static final int STORAGE_VALUE = 1;
    private static final String EXPECTED_REPORT = "fruit,quantity"
            + System.lineSeparator() + STORAGE_KEY + "," + STORAGE_VALUE;
    private static ReportGeneratorImpl reportGenerator;

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generateReport_withValidData_ok() {
        FruitStorage.fruits.put(STORAGE_KEY, STORAGE_VALUE);
        String actual = reportGenerator.generateReport();
        assertEquals(String.format("For valid data expected report -> %s%nbut was -> %s",
                EXPECTED_REPORT, actual), EXPECTED_REPORT, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
