package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String VALID_PATH_TO_VALID_FILE = "src/test/resources/valid_input.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/empty_input.csv";
    private static final String PATH_TO_NONEXISTENT_FILE = "path/to/nonexistent/file.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_file_ok() {
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of(VALID_PATH_TO_VALID_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        actual = readerService.readFromFile(VALID_PATH_TO_VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void test_read_empty_file_not_ok() {
        try {
            readerService.readFromFile(PATH_TO_EMPTY_FILE);
            fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("File is empty", e.getMessage());
        }
    }

    @Test
    public void test_read_nonexistent_file_not_ok() {
        try {
            readerService.readFromFile(PATH_TO_NONEXISTENT_FILE);
            fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Can't read file", e.getMessage());
        }
    }
}
