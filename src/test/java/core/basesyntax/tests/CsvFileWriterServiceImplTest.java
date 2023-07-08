package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterServiceImpl fileWriterService;
    private static String filePath;

    @BeforeAll
    public static void setUp() {
        fileWriterService = new CsvFileWriterServiceImpl();
        filePath = "src/main/java/core/basesyntax/outputFiles/output.csv";
    }

    @Test
    public void writeToFile_validData_Ok() {
        List<String> report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,10");
        report.add("apple,5");
        File file = new File(filePath);
        fileWriterService.writeToFile(report, filePath);
        assertTrue(file.exists());
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        List<String> report = new ArrayList<>();
        fileWriterService.writeToFile(report, filePath);
        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    public void writeToFile_invalidFilePath_NotOk() {
        String filePath = "invalid/file/path.csv";
        List<String> report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,10");

        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeToFile(report, filePath));
    }
}
