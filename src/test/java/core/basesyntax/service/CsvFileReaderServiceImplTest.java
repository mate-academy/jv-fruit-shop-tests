package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.testservice.RandomDataGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderService csvFileReaderService;
    private static RandomDataGenerator randomDataGenerator;
    private static final List<String> EXPECTED_LIST =
            List.of("b,banana,20", "b,apple,100", "s,banana,100",
            "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,banana,50");
    private static final String DATA_FOR_TEST = "src/test/resources/data_for_test.csv";
    private static final String EMPTY_FILE_FOR_TEST = "src/test/resources/empty-file.csv";

    @BeforeAll
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
        randomDataGenerator = new RandomDataGenerator();
    }

    @Test
    public void readDataFromFile_ReadData_Ok() {
        Assertions.assertEquals(EXPECTED_LIST, csvFileReaderService
                .readDataFromFile(DATA_FOR_TEST),
                "The actual list doesn't match the expected list");
    }

    @Test
    public void readDataFromFile_EmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        Assertions.assertEquals(expected,
                csvFileReaderService.readDataFromFile(EMPTY_FILE_FOR_TEST),
                "Reading data from empty file failed");
    }

    @Test
    public void readDataFromFile_NotExistingFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                csvFileReaderService.readDataFromFile(randomDataGenerator.generateRandomData()),
                "RuntimeException for non-existing file expected");
    }
}
