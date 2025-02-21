package core.basesyntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderServiceTest {

    @Test
    void readValidFile() {
        FileReaderService readerService = new FileReaderServiceImpl();
        List<String> result = readerService.read("src/test/resources/input.csv");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void readInvalidFile() {
        FileReaderService readerService = new FileReaderServiceImpl();
        List<String> result = readerService.read("src/test/resources/non_exiting_file.csv");
        assertTrue(result.isEmpty());
    }

    @Test
    void readEmptyFile() {
        FileReaderService readerService = new FileReaderServiceImpl();
        List<String> result = readerService.read("src/test/resources/empty.csv");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
