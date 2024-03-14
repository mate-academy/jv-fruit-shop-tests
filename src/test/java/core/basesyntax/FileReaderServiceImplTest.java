package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {

    @Test
    void readFromFile_existingFile_success() {
        ReaderService readerService = new FileReaderServiceImpl();
        String filePath = "src/test/resources/test_data.csv";
        List<String> data = readerService.readFromFile(filePath);
        assertFalse(data.isEmpty());
    }

    @Test
    void readFromFile_nonExistingFile_throwsException() {
        ReaderService readerService = new FileReaderServiceImpl();
        String filePath = "non_existing_file.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(filePath));
    }
}
