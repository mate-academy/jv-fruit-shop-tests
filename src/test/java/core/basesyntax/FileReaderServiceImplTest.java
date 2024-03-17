package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private final ReaderService readerService = new FileReaderServiceImpl();
    private final String validPath = "src/test/resources/test_data.csv";
    private final String invalidPath = "non_existing_file.csv";

    @Test
    void readFromFile_existingFile_success() {
        List<String> data = readerService.readFromFile(validPath);
        assertFalse(data.isEmpty());
    }

    @Test
    void readFromFile_nonExistingFile_throwsException() {
        String expectedMessage = "Can`t read from file " + invalidPath;
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(invalidPath));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
