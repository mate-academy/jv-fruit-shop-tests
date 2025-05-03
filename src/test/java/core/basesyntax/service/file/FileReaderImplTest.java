package core.basesyntax.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class FileReaderImplTest {
    private static final String TEST_INPUT_FILE_PATH = "src/test/resources/testInput.csv";
    private static final String TEST_WRONG_FILE_PATH = "src/test/resources/wrongPath.csv";
    private static final String TEST_EMPTY_FILE_PATH = "src/test/resources/wrongPath.csv";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_isOk() {
        String actualOutputString =
                fileReader.readFromFile(TEST_INPUT_FILE_PATH);
        String expectedOutputString = "type,fruit,quantity"
                + System.lineSeparator()
                + "b,banana,20"
                + System.lineSeparator()
                + "b,apple,100"
                + System.lineSeparator()
                + "s,banana,100"
                + System.lineSeparator()
                + "p,banana,13"
                + System.lineSeparator()
                + "r,apple,10"
                + System.lineSeparator()
                + "p,apple,20"
                + System.lineSeparator()
                + "p,banana,5"
                + System.lineSeparator()
                + "s,banana,50";
        assertEquals(expectedOutputString, actualOutputString, "Output string must be:\n"
                + expectedOutputString + "\n" + "but was:\n"
                + actualOutputString);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testInput.csv", numLinesToSkip = 1)
    void checkFileBeforeReading(String type, String fruit, Integer quantity) {
        assertNotNull(type);
        assertNotNull(fruit);
        assertNotNull(quantity);
        assertTrue(quantity >= 0);
    }

    @Test
    void readFromFileWithWrongPath_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(TEST_WRONG_FILE_PATH);
        });
        String expectedMassage =
                "Can't read from file " + TEST_WRONG_FILE_PATH;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void readFromEmptyFile_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(TEST_EMPTY_FILE_PATH);
        });
        String expectedMassage =
                "Can't read from file " + TEST_EMPTY_FILE_PATH;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
