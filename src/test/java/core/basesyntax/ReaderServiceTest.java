package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceTest {
    private static ReaderService readerService;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void reader_FileNotFoundNotOK() {
        final String inputFile = "./src/test/resources/notFound.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't read the file " + inputFile);
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void reader_FileIsEmptyNotOK() {
        final String inputFile = "./src/test/resources/empty.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("File " + inputFile + " is empty.");
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void reader_readFromFileOK() {
        final String inputFile = "./src/main/resources/readOk.txt";
        List<String> actual = readerService.readFromCsvFile(inputFile);
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10");
        assertEquals(expected, actual);
    }
}
