package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {

    private static final String NOT_EXISTED_BEFORE_WRITING_FILE_NAME =
            "src/test/resources/notExistedFile.csv";
    private static final String FILE_TO_WRITE_NAME = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void readerServiceInitialization() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToNullFile_notOk() {
        String record = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,10";
        writerService.write(null, record);
    }

    @Test
    public void writeToNotExistedFile_Ok() {
        String record = "fruit,quantity"
                + System.lineSeparator() + "banana,30"
                + System.lineSeparator() + "apple,40";
        boolean actual = writerService.write(NOT_EXISTED_BEFORE_WRITING_FILE_NAME, record);
        Assert.assertTrue(actual);
    }

    @Test
    public void writeToFile_Ok() {
        String record = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        boolean actual = writerService.write(FILE_TO_WRITE_NAME, record);
        Assert.assertTrue(actual);
    }

    @AfterClass
    public static void deleteFile() {
        try {
            Files.delete(Path.of(NOT_EXISTED_BEFORE_WRITING_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file: "
                    + NOT_EXISTED_BEFORE_WRITING_FILE_NAME);
        }
    }
}
