package core.basesyntax;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFromFile_validFile_ok() throws IOException {
        String testFilePath = "src/main/resources/testFile.csv";
        String fileContent = "type,fruit,quantity\nb,banana,20\ns,apple,50";
        Files.writeString(Path.of(testFilePath), fileContent);
        List<String> result = fileReader.readFromFile(testFilePath);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("type,fruit,quantity", result.get(0));
        Assertions.assertEquals("b,banana,20", result.get(1));
        Assertions.assertEquals("s,apple,50", result.get(2));
        Files.delete(Path.of(testFilePath));
    }

    @Test
    void readFromFile_emptyFile_ok() throws IOException {
        String emptyFilePath = "src/main/resources/emptyFile.csv";
        Files.createFile(Path.of(emptyFilePath));
        List<String> result = fileReader.readFromFile(emptyFilePath);
        Assertions.assertTrue(result.isEmpty());
        Files.delete(Path.of(emptyFilePath));
    }

    @Test
    void readFromFile_fileDoesNotExist_notOk() {
        String nonExistentFilePath = "src/main/resources/nonExistentFile.csv";

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile(nonExistentFilePath);
        });

        Assertions.assertTrue(exception.getMessage()
                .contains("Can't read file " + nonExistentFilePath));
    }

    @Test
    void readFromFile_directoryInsteadOfFile_notOk() {
        Path tempDirectory = null;
        try {
            tempDirectory = Files.createTempDirectory("testDirectory");
            Path finalTempDirectory = tempDirectory;
            RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
                fileReader.readFromFile(finalTempDirectory.toString());
            });

            Assertions.assertTrue(exception.getMessage()
                    .contains("Can't read file " + tempDirectory));
        } catch (IOException e) {
            Assertions.fail("Failed to create temporary directory", e);
        } finally {
            if (tempDirectory != null) {
                try {
                    Files.deleteIfExists(tempDirectory);
                } catch (IOException e) {
                    Assertions.fail("Failed to delete temporary directory", e);
                }
            }
        }
    }
}
