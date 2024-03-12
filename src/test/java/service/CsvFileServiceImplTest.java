package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileServiceImplTest {
    private static final String EXISTING_FILE = "src/test/resources/first.csv";
    private static final String NON_EXISTING_FILE = "src/test/resources/non-existing-file.csv";
    private static final String NON_EXISTING_PATH = "";
    private static final String DEST_FILE = "src/test/resources/report.csv";
    private static FileService fileService;

    @BeforeAll
    public static void setUp() {
        fileService = new CsvFileServiceImpl();
    }

    @Test
    void readFromFile_existingFile_ok() {
        List<String> expectedData = List.of("test");
        Assertions.assertEquals(expectedData, fileService.readFromFile(EXISTING_FILE));
    }

    @Test
    void readFromFile_nonExistingFile_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.readFromFile(NON_EXISTING_FILE),
                "Method should throw RuntimeException when reading from non existing file");
    }

    @Test
    void writeToFile_creatingFile_ok() {
        String expectedData = "p,apple,10";
        fileService.writeToFile(expectedData, DEST_FILE);
        assertTrue(Files.exists(Path.of(DEST_FILE)));
    }

    @Test
    void writeToFile_cantCreatingFile_notOk() {
        String expectedData = "test";
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(expectedData, NON_EXISTING_PATH),
                "Method should throw RuntimeException for non existing path");
    }

    @AfterEach
    void removeReport() throws IOException {
        if (Files.exists(Path.of(DEST_FILE))) {
            Files.delete(Path.of(DEST_FILE));
        }
    }
}
