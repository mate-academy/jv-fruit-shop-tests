package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src/test/resources/testInput.csv";
    private static final String WRONG_PATH_TO_FILE = "test/folder/test.csv";
    private static FileReaderImpl fileReader;

    @BeforeAll
    static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_wrongPathToFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileReader.readFromFile(WRONG_PATH_TO_FILE),
                "Wrong path to file: " + WRONG_PATH_TO_FILE
        );
    }

    @Test
    void readFromFile_validInput_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );
        List<String> actual = fileReader.readFromFile(PATH_TO_TEST_INPUT_FILE);
        assertIterableEquals(expected, actual);
    }
}
