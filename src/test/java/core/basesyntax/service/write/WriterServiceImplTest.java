package core.basesyntax.service.write;

import core.basesyntax.service.read.ReaderService;
import core.basesyntax.service.read.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String INPUT_PATH = "src/test/java/resources/validData.csv";
    private static final String OUTPUT_PATH = "src/test/java/resources/emptyFile.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static String strings;
    private static WriterService writerService;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        strings = new String();
        readerService = new ReaderServiceImpl();
        writerService = new WriterServiceImpl();
        strings = "type,fruit,quantity"
                + SEPARATOR + "b,banana,20"
                + SEPARATOR + "b,apple,100"
                + SEPARATOR + "s,banana,100";
    }

    @Test (expected = NullPointerException.class)
    public void write_nullString_NotOk() {
        writerService.write(OUTPUT_PATH, null);
    }

    @Test
    public void write_validPathFile_OK() {
        writerService.write(OUTPUT_PATH, strings);
        List<String> expected = readerService.read(INPUT_PATH);
        List<String> actual = readerService.read(OUTPUT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void write_nullPath_NotOk() {
        writerService.write(null, strings);
    }

    @Test (expected = RuntimeException.class)
    public void write_emptyPath_NotOk() {
        writerService.write("", strings);
    }

    @Test
    public void write_emptyData_Ok() {
        writerService.write(OUTPUT_PATH, "");
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.read(OUTPUT_PATH);
        Assert.assertEquals(expected, actual);
    }
}
