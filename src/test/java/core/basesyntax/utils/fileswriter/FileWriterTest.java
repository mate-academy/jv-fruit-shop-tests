package core.basesyntax.utils.fileswriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static FileWriter fileWriter;
    private Path tempFile;

    @BeforeAll
    public static void beforeAll() {
        fileWriter = new FileWriter();
    }

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("tempFile", ".csv");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void writeToFile_shouldThrowRuntimeException_ifFailedWriteToFile() {
        Path invalidPath = Path.of("/invalid/path/file.txt");
        byte[] content = "type,fruit,quantity".getBytes();

        Assertions.assertThrows(
                RuntimeException.class,
                () -> fileWriter.writeToFile(invalidPath, content),
                "Should throw RuntimeException if failed write to file"
        );
    }

    @Test
    public void writeToFile_shouldWriteContentToFile() throws IOException {
        byte[] content = "type,fruit,quantity".getBytes();

        fileWriter.writeToFile(tempFile, content);

        byte[] fileContent = Files.readAllBytes(tempFile);
        Assertions.assertArrayEquals(
                content,
                fileContent,
                "The actual file content must be equal to content passed to method"
        );
    }
}
