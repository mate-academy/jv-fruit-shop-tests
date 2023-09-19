package core.basesyntax.services;

import core.basesyntax.service.impl.ReadServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadServiceImplTest {
    private static final String VALID_PATH_TO_VALID_FILE = "src/test/java/resources/validInput.csv";
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
    public void test_ReadNonexistentFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                readService.readFromFile(PATH_TO_NONEXISTENT_FILE));
    }
}