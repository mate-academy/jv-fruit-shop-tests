package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String OUTPUT_FILE = "src/test/resources/output.csv";
    private static final String TEST_TEXT = "carrot,25";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerService_writeToFile_ok() {
        List<String> actual;
        List<String> expected = new ArrayList<>();
        expected.add(TEST_TEXT);
        writerService.writeToFile(TEST_TEXT, OUTPUT_FILE);
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("No such file at " + OUTPUT_FILE, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writerService_writeToNullFile_notOk() {
        writerService.writeToFile(TEST_TEXT, null);
    }
}
