package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadFileException;
import core.basesyntax.service.ReadCsvFileService;
import core.basesyntax.service.implementations.ReadCsvFileServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadCsvFileServiceTest {
    private static String SOURCE_FILE = "src/test/resources/NormalTestInput.csv";
    private static String NOT_EXIST_SOURCE_FILE = "src/test/resources/nonexistent-file.csv";
    private static ReadCsvFileService readCsvFileService;

    @BeforeAll
    static void setReadService() {
        readCsvFileService = new ReadCsvFileServiceImpl();
    }

    @Test
    void readFile_validData_okay() {
        List<String> data = assertDoesNotThrow(() -> readCsvFileService.readFile(SOURCE_FILE));
        List<String> expectedData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(data, expectedData);
    }

    @Test
    void readFile_missingFile_notOkay() {
        ReadFileException readFileException = assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(NOT_EXIST_SOURCE_FILE));
        String expectedMeassage = "Can`t read from file: " + NOT_EXIST_SOURCE_FILE;
        assertEquals(expectedMeassage, readFileException.getMessage());
    }

    @Test
    void readFile_nullFile_notOkay() {
        ReadFileException readFileException = assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(null));
        String expectedMeassage = "How did you dare to give null filename?";
        assertEquals(expectedMeassage, readFileException.getMessage());
    }
}
