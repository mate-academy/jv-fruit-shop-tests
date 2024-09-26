package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriterImpl fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeFile_validData_ok() throws IOException {
        String data = "test content";
        String filePath = "src/test/resources/outputFile.txt";
        fileWriter.write(data, filePath);
        String actual = new String(Files.readAllBytes(Paths.get(filePath)));
        assertTrue(actual.contains(data));
    }

    @Test
    public void writeFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileWriter.write("data", "invalid/path.txt"));
    }
}
