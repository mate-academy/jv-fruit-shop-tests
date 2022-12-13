package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String START_OF_REPORT = "fruit,quantity";
    private static final String BANANA_RESULT = "banana,152";
    private static final String APPLE_RESULT = "apple,90";

    private static ReportGenerator reportGenerator;
    private Map<String, Integer> transactionResultMap;
    private String expectedReport;

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Before
    public void setUp() {
        transactionResultMap = new HashMap<>();
        transactionResultMap.put("banana", 152);
        transactionResultMap.put("apple", 90);
        expectedReport = START_OF_REPORT + System.lineSeparator()
                + BANANA_RESULT + System.lineSeparator()
                + APPLE_RESULT;
    }

    @Test
    public void generateReport_ok() {
        String actualReport = reportGenerator.generateReport(transactionResultMap);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void keyAndValueNull_notOk() {
        transactionResultMap.put(null, null);
        String actualReport = reportGenerator.generateReport(transactionResultMap);
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void tearDown() {
        transactionResultMap.clear();
    }
}
