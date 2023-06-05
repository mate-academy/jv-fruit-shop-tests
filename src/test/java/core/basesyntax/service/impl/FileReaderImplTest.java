package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exeption.WrongFileFormatException;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileReaderImpl Test")
class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String VALID_DATA_PATH = "src/test/resources/ValidInputTestData1.csv";
    private static final String INVALID_WRONG_FORMAT = "src/test/resources/wrongFormat.txt";
    private static final String INVALID_DATA_PATH = "invalid/classpath";
    private static final String EMPTY_DATA_PATH = "src/test/resources/Empty.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @DisplayName("Check reader with valid file")
    @Test
    void readDataFromFile_validClasspath_ok() {
        List<String> expectedList = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actualList = fileReader
                .readDataFormFile(VALID_DATA_PATH);
        assertEquals(expectedList, actualList);
    }

    @DisplayName("Check reader with invalid file")
    @Test
    void readDataFromFile_invalidClasspath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readDataFormFile(INVALID_DATA_PATH),
                "Should throw an exception");
    }

    @DisplayName("Check reader with valid empty file")
    @Test
    void readDataFromFile_emptyFile_ok() {
        List<String> actualList = fileReader
                .readDataFormFile(EMPTY_DATA_PATH);
        assertTrue(actualList.isEmpty());
    }

    @DisplayName("Check reader with invalid file format")
    @Test
    void readFile_invalidFileExtension_notOk() {
        assertThrows(WrongFileFormatException.class, () ->
                        fileReader.readDataFormFile(INVALID_WRONG_FORMAT),
                "Should throw an exception");
    }
}
