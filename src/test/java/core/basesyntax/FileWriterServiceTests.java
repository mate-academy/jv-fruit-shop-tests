package core.basesyntax;

import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.FileReaderServiceImpl;
import core.basesyntax.services.FileWriterService;
import core.basesyntax.services.FileWriterServiceImpl;
import core.basesyntax.services.exception.FileWriterException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTests {
    private static final String file = "src/test/java/core/basesyntax/writeTest.csv";
    private static FileWriterService writerService;
    private static FileReaderService readerService;

    @BeforeClass
    public static void setWriterAndReaderServices() {
        writerService = new FileWriterServiceImpl();
        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void writeTextToFile_Ok() {
        String report = "banana,100" + System.lineSeparator()
                        + "apple,50";
        List<String> expected = List.of("banana,100", "apple,50");
        writerService.writeToFile(file, report);
        List<String> actual = readerService.readFromFile(file);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeNullToFile_NotOk() {
        writerService.writeToFile(file, null);
    }

    @Test
    public void writeReportToFileException_Ok() {
        String report = "banana,100" + System.lineSeparator()
                + "apple,50";
        try {
            writerService.writeToFile("src/test/java/core/basesyntax/111.ddd", report);
        } catch (FileWriterException ex) {
            Assert.assertEquals("Can't write data to file 111.csv", ex.getMessage());
        }
    }
}
