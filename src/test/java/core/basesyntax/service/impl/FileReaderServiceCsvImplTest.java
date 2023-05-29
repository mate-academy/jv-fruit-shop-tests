package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exeptions.WrongExtensionFile;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReaderCsvImpl Test")
class FileReaderServiceCsvImplTest {
    private static FileReaderServiceCsvImpl READER;

    @BeforeAll
    static void beforeAll() {
        READER = new FileReaderServiceCsvImpl();
    }

    @DisplayName("Check reader CSV with valid file")
    @Test
    void readFile_validClasspath_ok() {
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
        List<String> actualList = READER
                .readFile("src/test/resources/input/testFile.csv");
        assertEquals(expectedList, actualList);
    }

    @DisplayName("Check reader CSV with invalid file")
    @Test
    void readFile_invalidClasspath_notOk() {
        assertThrows(RuntimeException.class, () -> READER.readFile("invalid/classpath"),
                "Should throw an exception");
    }

    @DisplayName("Check reader CSV with valid empty file")
    @Test
    void readFile_emptyFile_ok() {
        List<String> actualList = READER
                .readFile("src/test/resources/input/empty.csv");
        assertTrue(actualList.isEmpty());
    }

    @DisplayName("Check reader CSV with one line file")
    @Test
    void readFile_oneLine_ok() {
        List<String> expectedList = List.of("type,fruit,quantity");
        List<String> actualList = READER
                .readFile("src/test/resources/input/oneLine.csv");
        assertEquals(expectedList, actualList);
    }

    @DisplayName("Check reader CSV with invalid file extension")
    @Test
    void readFile_invalidFileExtension_notOk() {

        assertThrows(WrongExtensionFile.class, () ->
                        READER.readFile("src/test/resources/input/wrongExtension"),
                "Should throw an exception");
    }
}
