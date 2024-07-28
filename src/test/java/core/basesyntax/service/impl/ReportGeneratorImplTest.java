package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final int QUANTITY_INITIAL_APPLE = 100;
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY_INITIAL_BANANA = 50;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void testGetReportWithFruits() {
        Storage.addFruit(FRUIT_APPLE, QUANTITY_INITIAL_APPLE);
        Storage.addFruit(FRUIT_BANANA, QUANTITY_INITIAL_BANANA);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();

        String sortedExpectedReport = Arrays.stream(expectedReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        String sortedActualReport = Arrays.stream(actualReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        assertEquals(sortedExpectedReport, sortedActualReport,
                "The report should match the expected output");
    }

    @Test
    void testGetReportEmptyStorage() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport,
                "The report for empty storage should only contain the header");
    }

    @Test
    void testGetReportWithSingleFruit() {
        Storage.addFruit(FRUIT_APPLE, QUANTITY_INITIAL_APPLE);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100";

        String actualReport = reportGenerator.getReport();

        assertEquals(expectedReport, actualReport,
                "The report should match the expected output for a single fruit");
    }
}
