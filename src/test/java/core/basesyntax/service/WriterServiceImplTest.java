package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/writeTestFile.csv";
    private static final String DEFAULT_TEXT = "testtext";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerService_writeToFile_OK() {
        writerService.writeToFile(FILE_PATH, DEFAULT_TEXT);
        List<String> actual = List.of(DEFAULT_TEXT);
        try {
            List<String> expected = Files.readAllLines(Path.of(FILE_PATH));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + FILE_PATH, e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeNullPathFile_NotOK() {
        writerService.writeToFile(null, "123");
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeNullText_NotOK() {
        writerService.writeToFile(FILE_PATH, null);
    }
}
