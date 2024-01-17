package core.basesyntax.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_INPUT_FILE_PATH = "src/test/resources/testInput.csv";
    private static final String TEST_WRONG_FILE_PATH = "src/test/resources/wrongPath.csv";
    private static final String TEST_EMPTY_FILE_PATH = "src/test/resources/wrongPath.csv";
    private static final String EXPECTED_OUTPUT_STRING = "type,fruit,quantity"
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
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_isOk() {
        String actualOutputString =
                fileReader.readFromFile(TEST_INPUT_FILE_PATH);
        assertEquals(EXPECTED_OUTPUT_STRING, actualOutputString, "Output string must be:\n"
                + EXPECTED_OUTPUT_STRING + "\n" + "but was:\n"
                + actualOutputString);
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
    void readFromEmptyFile_expectedExceptin() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(TEST_EMPTY_FILE_PATH);
        });
        String expectedMassage =
                "Can't read from file " + TEST_EMPTY_FILE_PATH;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }
}
