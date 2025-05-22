package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FileReader;

class FileReaderImplTest {
    private static FileReader fileReader;
    private static Path filePath;
    private static final String NON_EXISTENT_FILE = "nonExistent_file.csv";

    @BeforeAll
    static void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        filePath = Files.createTempFile("reportToRead", ".csv");
        Files.write(filePath, List.of("type,fruit,quantity", "b,banana,20", "b,apple,100"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test
    void read_validFile_success() {
        List<String> list = fileReader.read(filePath.toString());

        assertEquals(3, list.size());
        assertEquals("type,fruit,quantity", list.get(0));
        assertEquals("b,banana,20", list.get(1));
        assertEquals("b,apple,100", list.get(2));
    }

    @Test
    void failureReadFile_noOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXISTENT_FILE));

        assertTrue(exception.getMessage().contains("Can`t read file"));
    }
}
