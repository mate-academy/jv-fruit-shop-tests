package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_validFilePath_ok() throws RuntimeException {
        String validFilePath = "src/test/resources/validInput.csv";
        List<String> expectedContent = List.of("apple,10", "banana,20");
        List<String> actualContent = fileReader.readFile(validFilePath);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void readFile_existingEmptyFile_ok() throws RuntimeException {
        String emptyFilePath = "src/test/resources/emptyFile.csv";
        List<String> actualContent = fileReader.readFile(emptyFilePath);
        assertTrue(actualContent.isEmpty(), "The file should return an empty list"
                + " when no content is present.");
    }

    @Test
    void readFile_notExistingFile_notOk() {
        String invalidFilePath = "src/test/resources/nonexistent.csv";
        assertThrows(RuntimeException.class, () -> fileReader.readFile(invalidFilePath));
    }
}
