package core.basesyntax.services.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String EXISTING_REPORT = "src/main/resources/reports/report.csv";
    private static final String EMPTY_FILE = "src/main/resources/reports/empty-report.csv";
    private static final String NOT_EXISTING_REPORT = "src/main/resources/reports/no_exist.csv";
    private static final String CSV_SEPARATOR = ",";
    private static FileReaderServiceImpl fileReaderService;
    private static List<String> fruitReport;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        fruitReport = List.of("b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
    }

    @Test
    void readReport_existingReport_ok() {
        List<String> containText = fileReaderService.read(new File(EXISTING_REPORT));
        containText.remove(0);
        Assertions.assertEquals(fruitReport, containText);
    }

    @Test
    void readReport_noExistingReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.read(new File(NOT_EXISTING_REPORT)));
    }

    @Test
    void readReport_emptyFile_ok() {
        File empty = new File(EMPTY_FILE);
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.read(empty);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readReport_noInvalidLetters_ok() {
        File report = new File(EXISTING_REPORT);
        List<String> containText = fileReaderService.read(report);
        containText.remove(0);
        for (String line : containText) {
            String[] values = line.split(CSV_SEPARATOR);
            String type = values[0].trim();
            assertTrue(type.equals("b") || type.equals("p")
                    || type.equals("s") || type.equals("r"));
        }
    }
}
