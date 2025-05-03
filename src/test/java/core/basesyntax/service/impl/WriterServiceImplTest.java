package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static String filePathEmpty;
    private static String filePathWrongContent;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        filePathEmpty = "src/test/resources/empty.csv";
        filePathWrongContent = "src/test/resources/wrongcontent.csv";
    }

    @Test
    public void write_emptyFile_ok() {
        writerService.writeToFile("", filePathEmpty);
        String actual;
        try {
            actual = Files.readString(Path.of(filePathEmpty));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("", actual);
    }

    @Test
    public void write_wrongFileContent_ok() {
        writerService.writeToFile("wrong information", filePathWrongContent);
        String actual;
        try {
            actual = Files.readString(Path.of(filePathWrongContent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("wrong information", actual);
    }
}
