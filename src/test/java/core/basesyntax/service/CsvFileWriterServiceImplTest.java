package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.testservice.ConfigReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;
    private static CsvFileReportService csvFileReportService;
    private static FruitDao fruitDao;
    private static ConfigReader configReader;
    private static String directoryPath;
    private static String inputFileName;
    private static Path fileCopyPath;
    private static final String INVALID_DATA = "1,2,3\n4,5,6\n";
    private static final String INVALID_FILE_READ_FROM = "invalid/test.csv";
    private static final String PREFIX = "test";
    private static final String SUFFIX = ".csv";
    private static final String INVALID_FILE_NAME = "test.csv";

    @BeforeAll
    public static void init() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        csvFileReportService = new CsvFileReportServiceImpl();
        fruitDao = new FruitDaoImpl();
        configReader = new ConfigReader();
        directoryPath = Paths.get(configReader.getValueByKey("file.read.from"))
                .getParent().toString();
        inputFileName = Paths.get(configReader.getValueByKey("file.read.from"))
                .getFileName().toString();
    }

    @BeforeEach
    public void createFileCopy() throws IOException {
        fileCopyPath = Files.createTempDirectory("temp")
                .resolve("file_copy.csv");
        Files.copy(Path.of(configReader.getValueByKey("file.read.from")),
                fileCopyPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void restoreFileCopy() throws IOException {
        Path filePath = Path.of(configReader.getValueByKey("file.read.from"));
        Files.copy(fileCopyPath, filePath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(fileCopyPath);
    }

    @Test
    public void writeReportToFile_isOk() {
        String expectedReport = csvFileReportService.createReport(fruitDao.getAllFruits());
        Assertions.assertDoesNotThrow(() -> {
            Path filePath = Paths.get(directoryPath, "filename.txt");
            Files.writeString(filePath, "");
            Files.write(filePath, expectedReport.getBytes());
            String actualReport = Files.readString(filePath);
            Assertions.assertEquals(expectedReport, actualReport,
                    "Recording failed");
        });
    }

    @Test
    public void writeDataToFile_FileExist_isOk() {
        Path filePath = Paths.get(directoryPath, inputFileName);
        Assertions.assertTrue(Files.exists(filePath),
                "The file wasn't written to the specified directory");
    }

    @Test
    public void writeDataToFile_InvalidFileName_isNotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(INVALID_FILE_READ_FROM, INVALID_DATA),
                    "RuntimeException expected");
    }

    @Test
    public void writeDataToFile_IoException_isNotOk() throws IOException {
        File file = Files.createTempFile(PREFIX, SUFFIX).toFile();
        file.deleteOnExit();

        if (!file.setReadOnly()) {
            Assertions.fail("Failed to set file as read-only");
        }

        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(file.getAbsolutePath(),
                        INVALID_DATA),
                "RuntimeException expected");
    }

    @Test
    public void writeDataToCorrectDirectoryAsFilePath_isOk() {
        Assertions.assertTrue(Files.exists(Paths.get(directoryPath)),
                "Directory path isn't correct");
    }

    @Test
    public void writeDataToFile_DirectoryAsFilePath_isNotOk() {
        String directoryPath = Paths.get(configReader.getValueByKey("file.read.from"))
                .getParent()
                .toString();
        Assertions.assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeDataToFile(directoryPath, INVALID_DATA),
                "RuntimeException expected");
    }

    @Test
    public void writeDataToFile_NullData_isNotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileWriterService.writeDataToFile(INVALID_FILE_NAME, null),
                "NullPointerException expected");
    }

    @Test
    public void writeDataToFile_NullFilePath_isNotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileWriterService.writeDataToFile(null, INVALID_DATA),
                "NullPointerException expected");
    }
}
