package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/writeTestFile.csv";
    private static final String DEFAULT_TEXT = "testtext";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerService_writeToFile_OK() {
        writerService.writeToFile(FILE_PATH, DEFAULT_TEXT);
        List<String> actual = List.of(DEFAULT_TEXT);
        List<String> expected = readFromFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeNullPathFile_NotOK() {
        writerService.writeToFile(null, "123");
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeNullText_NotOK() {
        writerService.writeToFile(FILE_PATH, null);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + FILE_PATH, e);
        }
    }
}
