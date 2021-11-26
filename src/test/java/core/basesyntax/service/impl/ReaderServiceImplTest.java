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
    public static final String FILE_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_PATH = "path";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() throws IOException {
        List<String> actual = readerService.readFromFile(FILE_PATH);
        List<String> expected = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void nullPath_notOk() {
        readerService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void incorrectPath_notOk() {
        readerService.readFromFile(INCORRECT_PATH);
    }
}
