package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void readFromFile_validFile_success() throws IOException {
        List<String> lines = csvFileReaderService.readFromFile("src/test/resources/test_input.csv");
        assertFalse(lines.isEmpty());
    }

    @Test
    void readFromFile_fileNotFound_throwsException() {
        assertThrows(NoSuchFileException.class, () -> csvFileReaderService.readFromFile("invalid/path/to/file.csv"));
    }
}
