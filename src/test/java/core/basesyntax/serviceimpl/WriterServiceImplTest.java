package core.basesyntax.serviceimpl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH_TO_EMPTY_FILE = "src\\test\\empty.csv";
    private static final String PATH_TO_FILE_WITH_WRONT_CONTENT = "src\\test\\wrongdata.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void init() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_emptyFile_ok() {
        writerService.writeToFile("", PATH_TO_EMPTY_FILE);
        String actual;
        try {
            actual = Files.readString(Path.of(PATH_TO_EMPTY_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("", actual);
    }

    @Test
    public void write_wrongFileContent_ok() {
        writerService.writeToFile("wrong information", PATH_TO_FILE_WITH_WRONT_CONTENT);
        String actual;
        try {
            actual = Files.readString(Path.of(PATH_TO_FILE_WITH_WRONT_CONTENT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("wrong information", actual);
    }
}
