package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src\\test\\resources\\testInput.csv";
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> fileReader.readFromFile("test/folder/test.csv")
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
        Assertions.assertIterableEquals(expected, actual);
    }
}
