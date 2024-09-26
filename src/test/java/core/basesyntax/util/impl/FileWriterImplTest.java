package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/outputFile.txt";
    private static final String INVALID_FILE_PATH = "invalid/path.txt";
    private FileWriterImpl fileWriter;

    @BeforeEach
    public void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        Files.createDirectories(Paths.get("src/test/resources"));
        Files.createFile(Paths.get(VALID_FILE_PATH));
    }

    @Test
    public void writeFile_validData_ok() throws IOException {
        String data = "test content";
        fileWriter.write(data, VALID_FILE_PATH);
        String actual = new String(Files.readAllBytes(Paths.get(VALID_FILE_PATH)));
        assertTrue(actual.contains(data));
    }

    @Test
    public void writeFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileWriter.write("data", INVALID_FILE_PATH));
    }
}
