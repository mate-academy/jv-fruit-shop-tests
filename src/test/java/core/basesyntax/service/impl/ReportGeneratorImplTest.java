package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void testGetReportWithFruits() {
        Storage.addFruit("apple", 100);
        Storage.addFruit("banana", 50);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();

        // Сортируем строки отчета перед сравнением
        String sortedExpectedReport = Arrays.stream(expectedReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        String sortedActualReport = Arrays.stream(actualReport.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println("Sorted Expected Report:\n" + sortedExpectedReport);
        System.out.println("Sorted Actual Report:\n" + sortedActualReport);

        assertEquals(sortedExpectedReport, sortedActualReport,
                "The report should match the expected output");
    }

    @Test
    void testGetReportEmptyStorage() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();

        System.out.println("Expected Report:\n" + expectedReport);
        System.out.println("Actual Report:\n" + actualReport);

        assertEquals(expectedReport, actualReport,
                "The report for empty storage should only contain the header");
    }

    @Test
    void testGetReportWithSingleFruit() {
        Storage.addFruit("apple", 100);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100";

        String actualReport = reportGenerator.getReport();

        // Выводим фактический и ожидаемый отчеты для отладки
        System.out.println("Expected Report:\n" + expectedReport);
        System.out.println("Actual Report:\n" + actualReport);

        assertEquals(expectedReport, actualReport,
                "The report should match the expected output for a single fruit");
    }
}
