package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceReportCreatorServiceTest {
    private static final String REPORT_ANNOTATION = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private ReportCreatorService report;
    private final Map<String, Integer> testFruitStatisticGood = new LinkedHashMap<>();

    {
        testFruitStatisticGood.put(APPLE, 100);
        testFruitStatisticGood.put(BANANA, 200);
        testFruitStatisticGood.put(ORANGE, 300);
    }

    private final Map<String, Integer> testFruitStatisticBad = new LinkedHashMap<>();

    {
        testFruitStatisticBad.put(APPLE, 100);
        testFruitStatisticBad.put(BANANA, 200);
        testFruitStatisticBad.put(ORANGE, -300);
    }

    @BeforeEach
    void setUp() {
        report = new BalanceReportCreatorService();
    }

    @Test
    void createReport_Ok() {
        Storage.balanceStatistic.putAll(testFruitStatisticGood);
        List<String> actualReport = report.createReport();
        List<String> expectedReport = new ArrayList<>();
        expectedReport.add(REPORT_ANNOTATION);
        for (Map.Entry<String, Integer> stringIntegerEntry : testFruitStatisticGood.entrySet()) {
            String line = stringIntegerEntry.getKey() + SEPARATOR + stringIntegerEntry.getValue();
            expectedReport.add(line);
        }
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_negativeValue_notOk() {
        Storage.balanceStatistic.putAll(testFruitStatisticBad);
        assertThrows(IllegalStateException.class,
                () -> report.createReport());
    }

    @AfterEach
    void tearDown() {
        Storage.balanceStatistic.clear();
    }
}
