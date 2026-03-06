package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test_input.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/non_existent_file.csv";
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_ValidFile_Ok() {
        List<String> actual = fileReaderService.read(VALID_FILE_PATH);
        assertEquals(2, actual.size());
        assertEquals("type,fruit,quantity", actual.get(0));
        assertEquals("b,banana,20", actual.get(1));
    }

    @Test
    void read_emptyFile_Ok() {
        String emptyFilePath = "src/test/resources/empty_file.csv";
        List<String> actual = fileReaderService.read(emptyFilePath);
        assertEquals(0, actual.size());
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.read(INVALID_FILE_PATH));
    }
}
