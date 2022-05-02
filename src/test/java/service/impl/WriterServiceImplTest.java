package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.WriterService;

public class WriterServiceImplTest extends WriterServiceImpl {
    private static final String FILE_PATH = "src/test/resources/output.csv";
    private static final String INVALID_FILE_PATH = "src\\main/,resources/.,output.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_validPath_ok() {
        writerService.write(FILE_PATH, "output");
    }

    @Test
    public void write_successWrite_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,200" + System.lineSeparator()
                + "pinapple,300";
        writerService.write(FILE_PATH, expected);
        String actual = readFromTestFile();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void write_emptyOutput_notOk() {
        writerService.write(FILE_PATH, "");
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_notOk() {
        writerService.write(INVALID_FILE_PATH, "output");
    }

    @Test(expected = NullPointerException.class)
    public void write_nullOutput_notOk() {
        writerService.write(FILE_PATH, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyFilePath_notOk() {
        writerService.write(null, "output");
    }

    private String readFromTestFile() {
        String data;
        try {
            data = Files.readString(Path.of(WriterServiceImplTest.FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file "
                    + WriterServiceImplTest.FILE_PATH, e);
        }
        return data;
    }
}
