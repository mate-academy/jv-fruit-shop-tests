package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.util.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String INVALID_PATH = "invalid/path.txt";
    private static final String VALID_PATH = "src/test/resources/outputFile.txt";
    private FileWriter fileWriter = new FileWriterImpl();

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(VALID_PATH));
        Files.createFile(Paths.get(VALID_PATH));
    }

    @Test
    public void writeFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileWriter.write(INVALID_PATH, "data"));
    }

    @Test
    public void writeFile_validData_ok() throws IOException {
        fileWriter.write(VALID_PATH, "data");
    }
}
