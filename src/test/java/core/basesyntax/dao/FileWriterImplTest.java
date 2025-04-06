package core.basesyntax.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private static final String TEST_CONTENT = "Sample content for the file.";
    private static final String RESOURCES_DIR = "src/test/resources/";
    private static final String DEFAULT_FILE_NAME = "fileWriter.csv";
    private static final String CUSTOM_FILE_NAME = "testCustomFile.csv";
    private static final String EMPTY_CONTENT_FILE = "emptyContentFile.csv";
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @BeforeEach
    void setUp() {
        deleteTestFile(CUSTOM_FILE_NAME);
        deleteTestFile(EMPTY_CONTENT_FILE);
    }

    @AfterEach
    void tearDown() {
        deleteTestFile(CUSTOM_FILE_NAME);
        deleteTestFile(EMPTY_CONTENT_FILE);
    }

    private void deleteTestFile(String fileName) {
        try {
            Path path = Paths.get(RESOURCES_DIR, fileName);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred during cleanup: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldWriteContentToDefaultFile_whenFileNameIsNull() {
        Path defaultFilePath = Paths.get(RESOURCES_DIR, DEFAULT_FILE_NAME);
        fileWriter.writeFile(null, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(defaultFilePath));
        try {
            String writtenContent = Files.readString(defaultFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent);
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldWriteContentToSpecifiedFile_whenFileNameIsNotNull() {
        Path customFilePath = Paths.get(RESOURCES_DIR, CUSTOM_FILE_NAME);
        fileWriter.writeFile(RESOURCES_DIR + CUSTOM_FILE_NAME, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(customFilePath));
        try {
            String writtenContent = Files.readString(customFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent);
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldNotThrowException_whenFileAlreadyExists() {
        Path customFilePath = Paths.get(RESOURCES_DIR, CUSTOM_FILE_NAME);
        try {
            Files.createFile(customFilePath);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred during the test: " + e.getMessage());
        }
        fileWriter.writeFile(RESOURCES_DIR + CUSTOM_FILE_NAME, TEST_CONTENT);
        Assertions.assertTrue(Files.exists(customFilePath));
        try {
            String writtenContent = Files.readString(customFilePath);
            Assertions.assertEquals(TEST_CONTENT, writtenContent);
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void writeFile_shouldHandleEmptyContent() {
        Path emptyContentFile = Paths.get(RESOURCES_DIR, EMPTY_CONTENT_FILE);
        fileWriter.writeFile(RESOURCES_DIR + EMPTY_CONTENT_FILE, "");
        Assertions.assertTrue(Files.exists(emptyContentFile));
        try {
            String writtenContent = Files.readString(emptyContentFile);
            Assertions.assertEquals("", writtenContent);
        } catch (IOException e) {
            Assertions.fail("IOException occurred during the test: " + e.getMessage());
        }
    }
}
