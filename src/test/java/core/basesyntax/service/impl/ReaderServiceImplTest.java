package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String EMPTY_FILE_CSV = "src/test/java/resources/empty.csv";
    private static final String INPUT_FILE_CSV = "src/test/java/resources/input.csv";

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> actual = readerService.readFromFile(EMPTY_FILE_CSV);
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(EMPTY_FILE_CSV));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_ok() {
        List<String> actual = readerService.readFromFile(INPUT_FILE_CSV);
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(INPUT_FILE_CSV));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }
}
