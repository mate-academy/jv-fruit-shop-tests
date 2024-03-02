package core.basesyntax.service.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static core.basesyntax.util.FruitTestConstants.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.ReportServiceImpl;
import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String REPORT_HEADER = "fruit,quantity";
    private ReportService reportService;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_EmptyStorageReturnsOnlyHeader_Ok() {
        String expectedReport = REPORT_HEADER + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport,
                "Report from empty storage should only contain the header.");
    }

    @Test
    void createReport_NonEmptyStorageReturnsCorrectData_Ok() {
        fruitDao.putToStorage(APPLE, 20);
        fruitDao.putToStorage(BANANA, 30);

        String actualReport = reportService.createReport();
        String[] reportLines = actualReport.split(System.lineSeparator());
        List<String> expectedDataLines = new ArrayList<>(List.of("apple,20", "banana,30"));
        List<String> actualDataLines = Arrays.asList(reportLines).subList(1, reportLines.length);

        Collections.sort(expectedDataLines);
        Collections.sort(actualDataLines);

        assertEquals(REPORT_HEADER, reportLines[0],
                "The header does not match the expected format.");
        assertEquals(expectedDataLines, actualDataLines,
                "Report data lines do not match expected content for non-empty storage.");
    }

}
