package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String OUTPUT_FILE = "src/test/resources/output.csv";
    private static final String TEST_TEXT = "carrot,25";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void writerService_writeToFile_ok() {
        String actual;
        String expected = TEST_TEXT;
        writerService.writeToFile(TEST_TEXT, OUTPUT_FILE);
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE)).get(0);
        } catch (IOException e) {
            throw new RuntimeException("No such file at " + OUTPUT_FILE, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writerService_writeToFile_NotOk() {
        writerService.writeToFile(TEST_TEXT, null);
    }
}
