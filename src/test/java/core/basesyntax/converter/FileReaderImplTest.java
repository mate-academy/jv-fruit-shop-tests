package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid_data.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty_file";
    private static final String INVALID_FILE_PATH = "src/main/converter/valid_data.csv";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void readingFileData_isOk() {
        assertNotNull(VALID_FILE_PATH);

        List<String> expected = new ArrayList<>(Arrays.asList("type, fruit, quantity"));
        List<String> actual = fileReader.read(VALID_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void readingEmptyFile_returnsEmptyList() {
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertTrue(actual.isEmpty(), "The list should be empty when reading an empty file.");
    }

    @Test
    void readingInvalidFilePath_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(INVALID_FILE_PATH);
        });
    }
}
