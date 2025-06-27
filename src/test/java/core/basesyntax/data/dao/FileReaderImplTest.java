package core.basesyntax.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.exeption.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String TEST_FILE_CONTENT = "fruit,quantity\nbanana,108\napple,88";
    private static final String EMPTY_FILE_CONTENT = "";
    private static Path testFilePath;
    private static Path emptyFilePath;
    private static FileReader fileReader;
    private static List<String> EXPECTED_TEST_CONTENT;

    @BeforeAll
    static void beforeAll() throws IOException {
        fileReader = new FileReaderImpl();
        EXPECTED_TEST_CONTENT = List.of("fruit,quantity", "banana,108", "apple,88");

        testFilePath = Files.createTempFile("test", ".csv");
        Files.writeString(testFilePath, TEST_FILE_CONTENT);

        emptyFilePath = Files.createTempFile("empty", ".csv");
        Files.writeString(emptyFilePath, EMPTY_FILE_CONTENT);
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.deleteIfExists(testFilePath);
        Files.deleteIfExists(emptyFilePath);
    }

    @Test
    void read_fromExistentFile_Ok() {
        List<String> actual = fileReader.readFile(testFilePath.toString());
        assertEquals(EXPECTED_TEST_CONTENT, actual);
    }

    @Test
    void read_fromNonExistentFile_NotOk() {
        String nonExistentPath = "nonExistent" + System.nanoTime() + ".csv";
        FileException exception = assertThrows(FileException.class,
                () -> fileReader.readFile(nonExistentPath));
        assertEquals("Can't read data from file: " + nonExistentPath, exception.getMessage());
    }

    @Test
    void read_fromEmptyFile_Ok() {
        List<String> actual = fileReader.readFile(emptyFilePath.toString());
        assertEquals(Collections.emptyList(), actual);
    }
}

