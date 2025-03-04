package core.basesyntax.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private static final String TEST_CONTENT = "Sample content for the file.";
    private static final String DEFAULT_FILE_NAME = "fileWriter.csv";
    private static final String CUSTOM_FILE_NAME = "testCustomFile.csv";
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @BeforeEach
    void setUp() {
        deleteTestFile(DEFAULT_FILE_NAME);
        deleteTestFile(CUSTOM_FILE_NAME);
    }

    @AfterEach
    void tearDown() {
        deleteTestFile(DEFAULT_FILE_NAME);
        deleteTestFile(CUSTOM_FILE_NAME);
    }

    private void deleteTestFile(String fileName) {
        try {
            Path path = Path.of(fileName);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred during the setup of the test: "
                    + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldWriteContentToDefaultFile_whenFileNameIsNull() {
        Path defaultFilePath = Path.of(DEFAULT_FILE_NAME);
        fileWriter.writeFile(null, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(defaultFilePath), "Default file should be created.");

        try {
            String writtenContent = Files.readString(defaultFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent,
                    "Content of the default file should match the provided content.");
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldWriteContentToSpecifiedFile_whenFileNameIsNotNull() {
        Path customFilePath = Path.of(CUSTOM_FILE_NAME);
        fileWriter.writeFile(CUSTOM_FILE_NAME, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(customFilePath), "Custom file should be created.");

        try {
            String writtenContent = Files.readString(customFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent,
                    "Content of the custom file should match the provided content.");
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldCreateParentDirectory_whenDirectoryDoesNotExist() {
        String fileName = "nonExistingDir/testFile.csv";
        Path filePath = Path.of(fileName);

        fileWriter.writeFile(fileName, TEST_CONTENT);

        Assertions.assertTrue(Files.exists(filePath),
                "File in non-existing directory should be created.");

        try {
            String writtenContent = Files.readString(filePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent,
                    "Content of the file should match the provided content.");
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldNotThrowException_whenFileAlreadyExists() {
        Path customFilePath = Path.of(CUSTOM_FILE_NAME);

        try {
            Files.createFile(customFilePath);
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the setup of the test: "
                    + e.getMessage());
        }

        fileWriter.writeFile(CUSTOM_FILE_NAME, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(customFilePath),
                "File should still exist after writing.");

        try {
            String writtenContent = Files.readString(customFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent,
                    "Content of the file should match the provided content.");
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldHandleEmptyContent() {
        Path emptyContentFile = Path.of("emptyContentFile.csv");
        fileWriter.writeFile("emptyContentFile.csv", "");
        Assertions.assertTrue(Files.exists(emptyContentFile),
                "File should be created with empty content.");

        try {
            String writtenContent = Files.readString(emptyContentFile);
            Assertions.assertEquals("", writtenContent,
                    "Content of the file should be empty.");
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldThrowException_whenFileNameIsInvalid() {
        String invalidFileName = "invalid:/file\\name.csv";
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fileWriter.writeFile(invalidFileName, TEST_CONTENT);
        });
        Assertions.assertTrue(exception.getMessage().contains("Error while writing to file"),
                "Exception message should indicate an error while writing to the file.");
    }
}
