package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static String filePath;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_validFile_Ok() {
        String expected = "test report";
        filePath = "src/test/resources/writer_test_file.csv";
        writerService.writeToFile(expected, filePath);
        String actual;
        try {
            actual = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + filePath);
        }
        Assert.assertEquals("Wrong data", expected, actual);
    }

    @Test
    public void write_emptyFile_Ok() {
        filePath = "src/test/resources/empty_file.csv";
        writerService.writeToFile("", filePath);
        String actual;
        try {
            actual = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + filePath);
        }
        Assert.assertEquals("Wrong data", "", actual);
    }
}
