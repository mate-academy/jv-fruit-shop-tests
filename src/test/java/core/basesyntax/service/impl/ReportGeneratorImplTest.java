package core.basesyntax.service.impl;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BANANA;
import static core.basesyntax.TestConstants.DEFAULT_QUANTITY;
import static core.basesyntax.TestConstants.REPORT_HEADER;
import static core.basesyntax.TestConstants.ZERO_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private StringBuilder stringBuilder;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void beforeEach() {
        stringBuilder = new StringBuilder(REPORT_HEADER);
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void reportInfo_validReport_ok() {
        Storage.getFruits().put(BANANA, DEFAULT_QUANTITY);
        Storage.getFruits().put(APPLE, DEFAULT_QUANTITY);
        String actual = reportGenerator.getReport();
        String checked = stringBuilder.append(BANANA)
                .append(",")
                .append(DEFAULT_QUANTITY)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(",")
                .append(DEFAULT_QUANTITY)
                .append(System.lineSeparator())
                .toString();
        assertEquals(actual, checked);
    }

    @Test
    void reportInfo_validReportWithZero_ok() {
        Storage.getFruits().put(BANANA, ZERO_QUANTITY);
        Storage.getFruits().put(APPLE, ZERO_QUANTITY);
        String actual = reportGenerator.getReport();
        String checked = stringBuilder.append(BANANA)
                .append(",")
                .append(ZERO_QUANTITY)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(",")
                .append(ZERO_QUANTITY)
                .append(System.lineSeparator())
                .toString();
        assertEquals(actual, checked);
    }
}
