package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvWriterServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/testFileOutput.csv";
    private static final String WRONG_PATH = "/a/a/a/";
    private static final String EXPECTED = "Hello World!";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final WriterService writerService = new CsvWriterServiceImpl();
    private final ReaderService readerService = new CsvReaderServiceImpl();

    @Test
    public void writeDataIncorrectPath_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Can't write to file with path:  "));
        writerService.writeToFile(WRONG_PATH, EXPECTED);
    }

    @Test
    public void writeToFile_Ok() {
        List<String> expected = List.of(EXPECTED);
        writerService.writeToFile(CORRECT_PATH, EXPECTED);
        List<String> actual = readerService.readFile(CORRECT_PATH);
        assertEquals(expected, actual);
    }
}
