package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String file = "writeTest.csv";
    private static FileWriterService writerService;
    private static FileReaderService readerService;

    @BeforeClass
    public static void setWriterAndReaderServices() {
        writerService = new FileWriterServiceImpl();
        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void write_report_Ok() {
        String report = "banana,100" + System.lineSeparator()
                + "apple,50";
        List<String> expected = List.of("banana,100", "apple,50");
        writerService.write(file, report);
        List<String> actual = readerService.read(file);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void write_nullReport_notOk() {
        writerService.write(file, null);
    }
}
