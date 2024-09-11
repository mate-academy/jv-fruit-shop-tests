package core.basesyntax.service.fileservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderServiceImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/fileToReadTest.csv";
    private static final String INVALID_PATH_TO_FILE = "src/test/name.csv";
    private static final String SEPARATOR = "\n";
    private static final String EXPECTED = """
                type,fruit,quantity
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50
                """;
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readValidFile_ok() {
        List<String> actual = fileReaderService.readFromFile(PATH_TO_FILE);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(EXPECTED.split(SEPARATOR)[i], actual.get(i));
        }
    }

    @Test
    void readInvalidFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(INVALID_PATH_TO_FILE));
    }
}