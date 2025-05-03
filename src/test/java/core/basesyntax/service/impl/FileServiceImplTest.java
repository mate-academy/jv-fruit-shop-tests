package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class FileServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/TestFile.csv";
    private static final String INVALID_FILE_PATH = "nonexistent-directory/TestFile.csv";
    private FileService fileService = new FileServiceImpl();
    private String expectedText = "fruit,quantity";

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    public void readFromFile_ok() throws IOException {
        createTestFile(expectedText);

        List<String> lines = fileService.readFromFile(TEST_FILE_PATH);

        assertEquals(1, lines.size());
        assertEquals(expectedText, lines.get(0));
    }

    @Test
    public void writeToFile_ok() throws IOException {
        fileService.writeToFile(TEST_FILE_PATH, expectedText);

        String actualText = readTestFileContent();

        assertEquals(expectedText, actualText);
    }

    @Test
    public void writeToFileInNonExistentDirectory() {
        assertThrows(RuntimeException.class, () -> fileService
                .writeToFile(INVALID_FILE_PATH, expectedText));
    }

    @Test
    public void readFromNonExistentFile() {
        assertThrows(RuntimeException.class, () -> fileService
                .readFromFile(INVALID_FILE_PATH));
    }

    private void createTestFile(String content) throws IOException {
        Path filePath = Paths.get(TEST_FILE_PATH);
        Files.write(filePath, Arrays.asList(content));
    }

    private String readTestFileContent() throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
    }
}
