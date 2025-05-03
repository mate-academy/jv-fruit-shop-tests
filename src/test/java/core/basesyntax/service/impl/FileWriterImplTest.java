package core.basesyntax.service.impl;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeption.WrongFileFormatException;
import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileWriterImpl Test")
class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String VALID_DATA_PATH = "src/test/resources/ValidInputTestResult1.csv";
    private static final String INVALID_DATA_PATH =
            "src/test/resources/incorrectPath/ValidOutputTestResult1.csv";
    private static final String EMPTY_DATA_PATH = "";

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() {
        new File(VALID_DATA_PATH).delete();
    }

    @DisplayName("Check writing to file in correct path")
    @Test
    void writeDataToFile_correctPath_ok() {
        List<String> dataToWrite = List.of("banana,152", "apple,90");
        fileWriter.writeDataToFile(VALID_DATA_PATH, dataToWrite);
        List<String> expected = List.of("fruit,quantity", "banana,152","apple,90");
        List<String> actual;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                VALID_DATA_PATH))) {
            actual = bufferedReader.lines().collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file: "
                    + VALID_DATA_PATH, e);
        }
        assertEquals(expected, actual);
    }

    @DisplayName("Check writing to file in invalid path")
    @Test
    void writeDataToFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeDataToFile(INVALID_DATA_PATH, List.of()));
    }

    @DisplayName("Check writing to file with empty path")
    @Test
    void writeToFile_emptyPath_notOk() {
        assertThrows(WrongFileFormatException.class, () ->
                fileWriter.writeDataToFile(
                        EMPTY_DATA_PATH, List.of()));
    }
}
