package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String NOT_EXISTED_BEFORE_WRITING_FILE_NAME =
            "src/test/resources/notExistedFile.csv";
    private static final String EXPECTED_FILE_NAME = "src/test/resources/expected.csv";
    private static final String FILE_TO_WRITE_NAME = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void readerServiceInitialization() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_nullFile_notOk() {
        String record = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,10";
        try {
            writerService.write(null, record);
            Assert.fail("Expected NullPointerException");
        } catch (RuntimeException e) {
            Assert.assertEquals("Can't write data in file null", e.getMessage());
        }
    }

    @Test
    public void write_notExistedFile_ok() {
        String record = "fruit,quantity"
                + System.lineSeparator() + "banana,30"
                + System.lineSeparator() + "apple,40";
        boolean actual = writerService.write(NOT_EXISTED_BEFORE_WRITING_FILE_NAME, record);
        try {
            Files.delete(Path.of(NOT_EXISTED_BEFORE_WRITING_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + FILE_TO_WRITE_NAME, e);
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void write_toFile_ok() {
        String record = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        boolean actual = writerService.write(FILE_TO_WRITE_NAME, record);
        Assert.assertTrue(actual);
    }

    @Test
    public void write_checkContent_Ok() {
        String record = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        writerService.write(FILE_TO_WRITE_NAME, record);
        try {
            List<String> expected = Files.readAllLines(Path.of(EXPECTED_FILE_NAME));
            List<String> actual = Files.readAllLines(Path.of(FILE_TO_WRITE_NAME));
            Assert.assertEquals(actual,expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + FILE_TO_WRITE_NAME, e);
        }
    }
}
