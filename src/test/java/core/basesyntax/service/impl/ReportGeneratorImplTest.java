package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String HEADER_OF_REPORT = "fruit,quantity";
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    public void test_getReport_singleFruit() {
        Storage.setFruitQuantity("apple", 10);

        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append(HEADER_OF_REPORT)
                .append(System.lineSeparator())
                .append("apple,10");

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport.toString(), actualReport);
    }

    @Test
    public void test_getReport_multipleFruits() {
        Storage.setFruitQuantity("apple", 10);
        Storage.setFruitQuantity("banana", 20);

        StringBuilder expectedReport = new StringBuilder();
        expectedReport.append(HEADER_OF_REPORT)
                .append(System.lineSeparator())
                .append("apple,10")
                .append(System.lineSeparator())
                .append("banana,20");

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport.toString(), actualReport);
    }
}
