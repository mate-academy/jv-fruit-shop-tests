package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String PATH = "src/main/java/resources/file.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() throws IOException {
        List<String> actual = readerService.readFromFile(PATH);
        List<String> expected = Files.readAllLines(Path.of(PATH));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NonExistingPath_notOk() {
        readerService.readFromFile("");
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_NullPath_notOk() {
        readerService.readFromFile(null);
    }
}
