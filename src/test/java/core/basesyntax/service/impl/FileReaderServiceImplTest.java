package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_validFileName_ok() {
        Path path = Path.of("src/test/resources/reportToReadTest.csv");
        List<String> expected = List.of("b,banana,20", "b,apple,100");
        List<String> actual = fileReaderService.read(path.toString());
        assertEquals(expected, actual);
    }

    @Test
    void read_emptyFile_ok() {
        Path path = Path.of("src/test/resources/emptyFile.csv");
        List<String> actual = fileReaderService.read(path.toString());
        assertTrue(actual.isEmpty());
    }

    @Test
    void read_invalidFilePath_notOk() {
        String fileName = "src/test/resources/invalidFile.csv";
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(fileName));
        assertEquals("Can't read data from file" + fileName, exception.getMessage());
    }

    @Test
    void read_nullFileName_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(null));
        assertEquals("File name can't be null", exception.getMessage());
    }
}
