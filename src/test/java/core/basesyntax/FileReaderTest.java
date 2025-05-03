package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private final FileReader fileReader = new FileReaderImpl();
    private final List<Path> tempPaths = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (Path path : tempPaths) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                fail("Failed to delete temporary file or directory: " + path, e);
            }
        }
        tempPaths.clear();
    }

    @Test
    void readFromFile_validFile_ok() throws IOException {
        Path testFile = createTempFile("testFile.csv", """
                type,fruit,quantity
                b,banana,20
                s,apple,50
                """);

        List<String> result = fileReader.readFromFile(testFile.toString());
        assertEquals(3, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("b,banana,20", result.get(1));
        assertEquals("s,apple,50", result.get(2));
    }

    @Test
    void readFromFile_emptyFile_ok() throws IOException {
        Path emptyFile = createTempFile("emptyFile.csv", "");
        List<String> result = fileReader.readFromFile(emptyFile.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void readFromFile_fileDoesNotExist_notOk() {
        String nonExistentFilePath = "src/main/resources/nonExistentFile.csv";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(nonExistentFilePath);
        });
        assertTrue(exception.getMessage().contains("Can't read file " + nonExistentFilePath));
    }

    @Test
    void readFromFile_directoryInsteadOfFile_notOk() throws IOException {
        Path tempDirectory = Files.createTempDirectory("testDirectory");
        tempPaths.add(tempDirectory);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(tempDirectory.toString());
        });

        assertTrue(exception.getMessage().contains("Can't read file " + tempDirectory));
    }

    private Path createTempFile(String fileName, String content) throws IOException {
        Path tempFile = Files.createTempFile(fileName, null);
        Files.writeString(tempFile, content);
        tempPaths.add(tempFile);
        return tempFile;
    }
}
