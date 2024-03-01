package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.ReportServiceImpl;
import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private ReportService reportService;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @Test
    void createReport_EmptyStorageReturnsOnlyHeader_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport,
                "Report from empty storage should only contain the header.");
    }

    @Test
    void createReport_NonEmptyStorageReturnsCorrectData_Ok() {
        fruitDao.putToStorage("apple", 20);
        fruitDao.putToStorage("banana", 30);

        String actualReport = reportService.createReport();
        String[] reportLines = actualReport.split(System.lineSeparator());
        List<String> expectedDataLines = new ArrayList<>(List.of("apple,20", "banana,30"));
        List<String> actualDataLines = Arrays.asList(reportLines).subList(1, reportLines.length);

        Collections.sort(expectedDataLines);
        Collections.sort(actualDataLines);

        assertEquals("fruit,quantity", reportLines[0],
                "The header does not match the expected format.");
        assertEquals(expectedDataLines, actualDataLines,
                "Report data lines do not match expected content for non-empty storage.");
    }

}
