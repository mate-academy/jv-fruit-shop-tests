package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Constants;
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
    private static final String REPORT_ANNOTATION = Constants.REPORT_ANNOTATION;
    private static final String SEPARATOR = Constants.SEPARATOR;
    private static final String APPLE = Constants.APPLE;
    private static final String BANANA = Constants.BANANA;
    private static final String ORANGE = Constants.ORANGE;
    private ReportCreatorService report;
    private final Map<String, Integer> testFruitStatisticGood = new LinkedHashMap<>();
    private final Map<String, Integer> testFruitStatisticBad = new LinkedHashMap<>();

    @BeforeEach
    void setUp() {
        report = new BalanceReportCreatorService();
        testFruitStatisticGood.put(APPLE, 100);
        testFruitStatisticGood.put(BANANA, 200);
        testFruitStatisticGood.put(ORANGE, 300);
        testFruitStatisticBad.put(APPLE, 100);
        testFruitStatisticBad.put(BANANA, 200);
        testFruitStatisticBad.put(ORANGE, -300);
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
