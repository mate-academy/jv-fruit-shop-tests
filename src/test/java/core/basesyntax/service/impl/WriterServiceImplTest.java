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
        filePath = "src/test/resources/empty.csv";
    }

    @Test
    public void write_emptyFile_ok() {
        writerService.writeToFile("", filePath);
        String actual;
        try {
            actual = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("", actual);
    }
}
