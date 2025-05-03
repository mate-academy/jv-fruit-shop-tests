package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceTest {
    private static final String INPUT_FILE_NAME = "src/test/resources/input.csv";
    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileService();
    }

    @Test
    void readFromFile_nullFileName_notOk() {
        assertThrows(NullPointerException.class, () -> fileService.readFromFile(null));
    }

    @Test
    void writeToFile_nullFileName_notOk() {
        assertThrows(NullPointerException.class, () -> fileService.writeToFile(null, ""));
    }

    @Test
    void readFromFile_notExistingFileName_notOk() {
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(""));
    }

    @Test
    void writeToFile_notExistingFileName_notOk() {
        assertThrows(RuntimeException.class, () -> fileService.writeToFile("", ""));
    }

    @Test
    void readFromFile_existingFileName_ok() {
        Assertions.assertNotEquals(null, fileService.readFromFile(INPUT_FILE_NAME));
    }
}
