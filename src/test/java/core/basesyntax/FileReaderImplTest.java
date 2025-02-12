package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.impl.FileReaderImpl;
import core.basesyntax.service.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test_file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private static final String NON_EXIST_FILE_PATH = "src/test/resources/missing.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        createTestFile(TEST_FILE_PATH, "line1\nline2\nline3");
        createTestFile(EMPTY_FILE_PATH, "");
    }

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read(TEST_FILE_PATH);
        assertEquals(3, result.size());
        assertEquals("line1", result.get(0));
        assertEquals("line2", result.get(1));
        assertEquals("line3", result.get(2));
    }

    @Test
    void read_emptyFile_ok() {
        List<String> result = fileReader.read(EMPTY_FILE_PATH);
        assertTrue(result.isEmpty());
    }

    @Test
    void read_nonExistentFile_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXIST_FILE_PATH));
        assertTrue(exception.getMessage().contains("Cannot read file"));
    }

    private void createTestFile(String path, String content) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}
