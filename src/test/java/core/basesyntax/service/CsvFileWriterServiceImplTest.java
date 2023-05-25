package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.testservice.RandomDataGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;
    private static RandomDataGenerator randomDataGenerator;
    private static Path actualReportTestPath;
    private static final String INVALID_FILE_READ_FROM = "invalid/test.csv";
    private static final String PREFIX = "test";
    private static final String SUFFIX = ".csv";
    private static final String ACTUAL_REPORT_TEST = "src/test/resources/report-actual.csv";

    @BeforeAll
    public static void init() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        randomDataGenerator = new RandomDataGenerator();
        actualReportTestPath = Paths.get(ACTUAL_REPORT_TEST);
    }

    @AfterEach
    public void restoreFileContent() throws IOException {
        Files.write(Paths.get(ACTUAL_REPORT_TEST), new byte[0]);
    }

    @Test
    public void writeDataToFile_WriteValidData_Ok() throws IOException {
        String report = createTestReport();
        csvFileWriterService.writeDataToFile(ACTUAL_REPORT_TEST, report);
        File actualFile = new File(ACTUAL_REPORT_TEST);
        String actual = String.join(System.lineSeparator(),
                Files.readAllLines(actualFile.toPath()));
        Assertions.assertEquals(report, actual,
                "Process of writing data to file failed");
    }

    @Test
    public void writeDataToFile_FileExist_Ok() {
        csvFileWriterService.writeDataToFile(ACTUAL_REPORT_TEST, createTestReport());
        Assertions.assertTrue(Files.exists(actualReportTestPath.getParent()),
                "The parent directory doesn't exist");
        Assertions.assertTrue(Files.exists(actualReportTestPath),
                "The file doesn't exist");
    }

    @Test
    public void writeDataToFile_InvalidFileName_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(INVALID_FILE_READ_FROM,
                        "test data"),
                    "RuntimeException expected");
    }

    @Test
    public void writeDataToFile_setReadOnly_Ok() throws IOException {
        File file = Files.createTempFile(PREFIX, SUFFIX).toFile();
        file.deleteOnExit();

        if (!file.setReadOnly()) {
            Assertions.fail("Failed to set file as read-only");
        }
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(file.getAbsolutePath(),
                        randomDataGenerator.generateRandomData()),
                "RuntimeException expected");
    }

    @Test
    public void writeDataToFile_DirectoryAsFilePath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService
                        .writeDataToFile(actualReportTestPath.getParent().toString(),
                        randomDataGenerator.generateRandomData()),
                "RuntimeException expected");
    }

    @Test
    public void writeDataToFile_NullData_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileWriterService
                        .writeDataToFile(randomDataGenerator.generateRandomData(),
                        null),
                "NullPointerException expected");
    }

    @Test
    public void writeDataToFile_NullFilePath_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileWriterService.writeDataToFile(null,
                        "src/test/resources/"),
                "NullPointerException expected");
    }

    public String createTestReport() {
        CsvFileReportServiceImpl csvFileReportService = new CsvFileReportServiceImpl();
        Map<String, Integer> allFruits = new HashMap<>();
        allFruits.put("banana", 30);
        allFruits.put("apple", 40);
        return csvFileReportService.createReport(allFruits);
    }
}
