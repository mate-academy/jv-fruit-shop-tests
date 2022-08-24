package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceTest {
    private static WriterService writerService;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writer_FileNotFoundNotOK() {
        final String outputFile = "./src/test/notFound/someFile.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't write the file " + outputFile);
        writerService.writeToCsvFile("fruit,quantity", outputFile);
    }

    @Test
    public void writer_writeDataOK() {
        final String outputFile = "./src/test/resources/outputTestOK.txt";
        ReaderService readerService = new ReaderServiceImpl();
        writerService.writeToCsvFile("fruit,quantity\r\nbanana,20\r\napple,10", outputFile);
        List<String> expected = readerService.readFromCsvFile(outputFile);
        List<String> actual = List.of("fruit,quantity", "banana,20", "apple,10");
        assertEquals(expected, actual);
    }
}
