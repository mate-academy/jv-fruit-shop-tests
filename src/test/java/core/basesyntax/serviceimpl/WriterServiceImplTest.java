package core.basesyntax.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static String filePathEmpty;
    private static String filePathWrongContent;
    private static WriterServiceImpl writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        filePathEmpty = "src\\test\\empty.csv";
        filePathWrongContent = "src\\test\\wrongdata.csv";
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
