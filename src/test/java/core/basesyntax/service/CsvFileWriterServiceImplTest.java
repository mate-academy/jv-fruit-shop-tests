package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.testservice.ConfigReader;
import core.basesyntax.testservice.RandomDataGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;
    private static ConfigReader configReader;
    private static RandomDataGenerator randomDataGenerator;
    private static Path fileCopyPath;
    private static final String INVALID_FILE_READ_FROM = "invalid/test.csv";
    private static final String PREFIX = "test";
    private static final String SUFFIX = ".csv";
    private static String actualReportTest;

    @BeforeAll
    public static void init() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        configReader = new ConfigReader();
        randomDataGenerator = new RandomDataGenerator();
    }

    @BeforeEach
    public void createFileCopy() throws IOException {
        fileCopyPath = Path.of("src/test/resources", "file_copy.csv");
        Files.copy(Path.of(configReader.getValueByKey("file.read.from")),
                fileCopyPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void restoreFileContent() throws IOException {
        actualReportTest = configReader.getValueByKey("report.actual.test");
        Files.write(Paths.get(actualReportTest), new byte[0]);
        Files.delete(fileCopyPath);
    }

    @Test
    public void writeDataToFile_WriteValidData_Ok() {
        csvFileWriterService.writeDataToFile(actualReportTest, createTestReport());
        File actualFile = new File(actualReportTest);
        File expectedFile = new File(configReader.getValueByKey("report.expected.test"));
        try {
            Assertions.assertTrue(FileUtils.contentEquals(expectedFile, actualFile),
                    "Files don't equal");
        } catch (IOException e) {
            throw new RuntimeException("Can't write file:" + actualReportTest, e);
        }
    }

    @Test
    public void writeDataToFile_FileExist_Ok() {
        Assertions.assertTrue(Files.exists(Paths.get(fileCopyPath.getParent().toString(),
                        fileCopyPath.getFileName().toString())),
                "The file wasn't written to the specified directory");
    }

    @Test
    public void writeDataToFile_InvalidFileName_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(INVALID_FILE_READ_FROM,
                        randomDataGenerator.generateRandomData()),
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
                        .writeDataToFile(fileCopyPath.getParent().toString(),
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
                        randomDataGenerator.generateRandomData()),
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
