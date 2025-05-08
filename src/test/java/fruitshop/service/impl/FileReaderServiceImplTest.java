package fruitshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_file.csv";
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_existingFile_returnsLinesOk() throws IOException {
        List<String> expectedLines = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50"
        );
        Files.write(Paths.get(TEST_FILE), expectedLines);

        List<String> actualLines = fileReaderService.read(TEST_FILE);

        assertEquals(expectedLines, actualLines);
    }

    @Test
    void read_missingFile_notOk() {
        String noSuchFile = "no_such_file.csv";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(noSuchFile));

        assertTrue(exception.getMessage().contains("Can't read file"));
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(NullPointerException.class, () -> fileReaderService.read(null));
    }
}
