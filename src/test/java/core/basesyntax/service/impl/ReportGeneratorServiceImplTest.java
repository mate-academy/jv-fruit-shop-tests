package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,40";
    private static final String EMPTY_REPORT = "fruit,quantity" + System.lineSeparator();
    private static ReportGeneratorService reportGenerator;
    private Map<String, Integer> input = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void generateReport_validData_Ok() {
        input.put(BANANA, 50);
        input.put(APPLE, 40);
        String resultReport = reportGenerator.generateReport(input);
        assertEquals(EXPECTED_REPORT, resultReport);
    }

    @Test
    public void generateReport_EmptyReport_Ok() {
        String resultReport = reportGenerator.generateReport(input);
        assertEquals(EMPTY_REPORT, resultReport);
    }
}
