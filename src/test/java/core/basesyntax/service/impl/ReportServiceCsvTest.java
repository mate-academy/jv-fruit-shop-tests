package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceCsvTest {
    private static final int DEFAULT_VALUE = 100;
    private static ReportServiceCsv reportServiceCsv;
    private static final String DEFAULT_FRUIT = "apple";
    private static final String CSV_SEPARATOR = ",";
    private static final String HEADER = "fruit" + CSV_SEPARATOR + "quantity";
    private static final Map<String, Integer> storage = new HashMap<>();
    private static final Collection<String> expectedResult = new ArrayList<>();

    @BeforeClass
    public static void initialize() {
        reportServiceCsv = new ReportServiceCsv(HEADER, CSV_SEPARATOR);
        expectedResult.add(HEADER);
        expectedResult.add(DEFAULT_FRUIT + CSV_SEPARATOR + DEFAULT_VALUE);
    }

    @Before
    public void initializeStorage() {
        storage.put(DEFAULT_FRUIT, DEFAULT_VALUE);
    }

    @After
    public void cleanup() {
        storage.clear();
    }

    @Test
    public void generateReport_withValidOperations_ok() {
        Assert.assertEquals(reportServiceCsv.generateReport(storage.entrySet()), expectedResult);
    }

    @Test(expected = NullPointerException.class)
    public void generateReport_withNull_notOk() {
        reportServiceCsv.generateReport(null);
    }
}
