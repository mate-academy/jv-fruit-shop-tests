package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String INVALID_PATH = "invalid/path.csv";
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        List<String> lines = fileReader.read("src/test/resources/toRead.csv");
        assertNotNull(lines, "The returned list should not be null");
        assertEquals(9, lines.size(), "The file should contain 9 lines");
        assertEquals(HEADER, lines.get(0), "The header line does not match");
        assertEquals("b,banana,20", lines.get(1), "The first data line does not match");
        assertEquals("b,apple,100", lines.get(2), "The second data line does not match");
    }

    @Test
    void read_invalidFilePath_throwsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INVALID_PATH));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(INVALID_PATH);
        });

        String expectedMessage = "Failed to read file: " + INVALID_PATH;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }
}
