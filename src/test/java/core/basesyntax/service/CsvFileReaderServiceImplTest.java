package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.testservice.ConfigReader;
import core.basesyntax.testservice.RandomDataGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
    private static RandomDataGenerator randomDataGenerator;
    private static final List<String> EXPECTED_LIST =
            List.of("b,banana,20", "b,apple,100", "s,banana,100",
            "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,banana,50");

    @BeforeAll
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
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
    public void restoreFileCopy() throws IOException {
        Files.delete(fileCopyPath);
    }

    @Test
    public void readDataFromFile_ReadData_Ok() {
        Assertions.assertEquals(EXPECTED_LIST,
                csvFileReaderService.readDataFromFile(fileCopyPath.toString()),
                "The actual list doesn't match the expected list");
    }

    @Test
    public void readDataFromFile_EmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        Assertions.assertEquals(expected,
                csvFileReaderService.readDataFromFile(configReader
                        .getValueByKey("empty.file.test")),
                "Reading data from empty file failed");
    }

    @Test
    public void readDataFromFile_NotExistingFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                csvFileReaderService.readDataFromFile(randomDataGenerator.generateRandomData()),
                "RuntimeException for non-existing file expected");
    }
}
