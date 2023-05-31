package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_FILE = "src/main/java/resources/testWrite.csv";
    private static final List<String> TEST_DATA = List.of("b,banana,20");
    private static WriterService writerService;

    @BeforeClass
    public static void init() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() throws IOException {
        writerService.writeToFile(TEST_DATA, TEST_FILE);
        List<String> actual = Files.readAllLines(Path.of(TEST_FILE));
        assertEquals(actual, TEST_DATA);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        writerService.writeToFile(TEST_DATA, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        writerService.writeToFile(TEST_DATA, "");
    }
}
