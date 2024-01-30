package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String NONEXISTENT_FILE_NAME = "src/test/resources/invalid.csv";
    private static final String CORRECT_FILE_NAME = "src/test/resources/correct.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_nonexistentFileName_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(NONEXISTENT_FILE_NAME));
    }

    @Test
    void read_successfulRead_Ok() {
        List<String> actualFileContent = fileReader.read(CORRECT_FILE_NAME);
        List<String> expectedContent = List.of("b,banana,20", "s,apple,100");
        assertEquals(expectedContent, actualFileContent);
    }

    @Test
    void read_nullFileRead_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(null));
    }
}
