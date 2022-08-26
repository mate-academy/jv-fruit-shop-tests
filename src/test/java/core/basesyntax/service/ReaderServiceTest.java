package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceTest {
    private static ReaderService readerService;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromCsvFile_fileNotFound_notOk() {
        final String inputFile = "./src/test/resources/notFound.txt";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't read the file " + inputFile);
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void readFromCsvFile_readFromFile_Ok() {
        final String inputFile = "./src/main/resources/readOk.txt";
        List<String> actual = readerService.readFromCsvFile(inputFile);
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10");
        assertEquals(expected, actual);
    }
}
