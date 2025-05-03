package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;
    private static List<String> expectedData;
    private static final String CORRECT_FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/invalidReportToRead.csv";

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expectedData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFromFile_CorrectPath_Ok() {
        List<String> readData = fileReaderService.read(CORRECT_FILE_PATH);
        assertEquals(expectedData, readData);
    }

    @Test
    void readFromFile_IncorrectPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.read(INCORRECT_FILE_PATH));
    }
}
