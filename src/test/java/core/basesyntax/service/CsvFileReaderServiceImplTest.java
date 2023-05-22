package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.testservice.ConfigReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderService csvFileReaderService;
    private static ConfigReader configReader;
    private static Path fileCopyPath;
    private static final List<String> EXPECTED_LIST =
            List.of("b,banana,20", "b,apple,100", "s,banana,100",
            "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,banana,50");
    private static final String NON_EXISTING_FILE = "non-existing-file.csv";

    @BeforeAll
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
        configReader = new ConfigReader();
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
    public void readDataFromFile_isOk() {
        List<String> actual = csvFileReaderService
                .readDataFromFile(configReader.getValueByKey("file.read.from"));
        Assertions.assertEquals(EXPECTED_LIST, actual,
                "The actual list doesn't match the expected list");
    }

    @Test
    public void readDataFromFile_nullFilePath_isNotOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                csvFileReaderService.readDataFromFile(null),
                "NullPointerException for null file path expected");
    }

    @Test
    public void readDataFromNotExistingFile_isNotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                csvFileReaderService.readDataFromFile(NON_EXISTING_FILE),
                "RuntimeException for non-existing file expected");
    }
}
