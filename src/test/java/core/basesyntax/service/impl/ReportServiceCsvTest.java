package core.basesyntax.service.impl;

import core.basesyntax.db.DaoService;
import core.basesyntax.db.DaoServiceHashMap;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceCsvTest {
    private static final int DEFAULT_VALUE = 100;
    private static ReportServiceCsv reportServiceCsv;
    private static final String DEFAULT_FRUIT = "apple";
    private static final String CSV_SEPARATOR = ",";
    private static final String HEADER = "fruit" + CSV_SEPARATOR + "quantity";
    private static final DaoService STORAGE = new DaoServiceHashMap();
    private static final Collection<String> EXPECTED_RESULT = new ArrayList<>();

    @BeforeClass
    public static void initialize() {
        reportServiceCsv = new ReportServiceCsv(HEADER, CSV_SEPARATOR);
        STORAGE.create(DEFAULT_FRUIT, DEFAULT_VALUE);
        EXPECTED_RESULT.add(HEADER);
        EXPECTED_RESULT.add(DEFAULT_FRUIT + CSV_SEPARATOR + DEFAULT_VALUE);
    }

    @Test
    public void generateReport_withValidOperations_ok() {
        Assert.assertEquals(reportServiceCsv.generateReport(STORAGE), EXPECTED_RESULT);
    }

    @Test
    public void generateReport_withNull_notOk() {
        try {
            reportServiceCsv.generateReport(null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }
    }
}
