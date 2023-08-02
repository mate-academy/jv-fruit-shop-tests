package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private static final String VALID_PATH_TO_VALID_FILE = "src/test/resources/valid_input.csv";
    private static final String PATH_TO_NONEXISTENT_FILE = "path/to/nonexistent/file.csv";

    private static ReadServiceImpl readService;

    @BeforeAll
    public static void beforeAll() {
        readService = new ReadServiceImpl();
    }

    @Test
    public void test_ReadFile_Ok() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(VALID_PATH_TO_VALID_FILE));
        List<String> actual = readService.readFromFile(VALID_PATH_TO_VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void test_ReadNonexistentFile_NotOk() {
        assertThrows(RuntimeException.class, () ->
                readService.readFromFile(PATH_TO_NONEXISTENT_FILE));
    }
}
